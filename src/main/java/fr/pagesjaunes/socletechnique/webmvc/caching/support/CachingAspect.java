package fr.pagesjaunes.socletechnique.webmvc.caching.support;

import fr.pagesjaunes.socletechnique.webmvc.caching.annotation.Cacheable;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * AOP aspect that handles 'Cache-Control' response header, based on the
 * {@link Cacheable} annotation. A 'Cache-Control' header will be generated in the response
 * for rest controller methods annotated with the annotation.
 */
@Aspect
@Slf4j
@NoArgsConstructor
public class CachingAspect {

    private static final String X_CACHE_GRACE_HEADER_NAME = "X-CACHE-GRACE";

    @Autowired
    private HttpServletResponse responseObject;

    private final ConcurrentHashMap<String, CachingInfo> cachingInfos = new ConcurrentHashMap<>();

    public CachingAspect(HttpServletResponse pResponseObject) {
        this.responseObject = pResponseObject;
    }

    @Around("@annotation(fr.pagesjaunes.socletechnique.webmvc.caching.annotation.Cacheable)")
    Object proceed(ProceedingJoinPoint pjp) throws Throwable {
        Object returnedValue = pjp.proceed();
        CachingInfo ci = getCachingInfos(pjp.getSignature());
        if (ci.isSupported()) {
            responseObject.addHeader(HttpHeaders.CACHE_CONTROL, ci.getCacheControl().getHeaderValue());
            if (ci.getGraceValue() != null) {
                responseObject.addHeader(X_CACHE_GRACE_HEADER_NAME, ci.getGraceValue());
            }
        }

        return returnedValue;
    }

    private @NonNull
    CachingInfo getCachingInfos(@NonNull Signature signature) {
        return cachingInfos.computeIfAbsent(signature.toLongString(), k -> {
            if (MethodSignature.class.isAssignableFrom(signature.getClass())) {
                MethodSignature ms = (MethodSignature) signature;

                // Compute the caching info for the method. These infos are
                // only computed on first access and are then stored in a map for later access.
                if (ms.getMethod() != null) {
                    Cacheable annot = ms.getMethod().getAnnotation(Cacheable.class);
                    if (annot != null) {
                        CacheControl cacheControl = null;
                        String graceValue = null;
                        if (annot.noStore()) {
                            cacheControl = CacheControl.noStore();
                        } else if (annot.noCache()) {
                            cacheControl = CacheControl.noCache();
                        } else if (annot.maxAge() >= 0) {
                            cacheControl = CacheControl.maxAge(annot.maxAge(), TimeUnit.SECONDS);
                        }
                        if (cacheControl != null) {
                            if (annot.sMaxAge() >= 0) {
                                cacheControl.sMaxAge(annot.sMaxAge(), TimeUnit.SECONDS);
                            }
                            if (annot.mustRevalidate()) {
                                cacheControl.mustRevalidate();
                            }
                            if (annot.noTransform()) {
                                cacheControl.noTransform();
                            }
                            if (annot.privateFlag()) {
                                cacheControl.cachePrivate();
                            }
                            if (annot.proxyRevalidate()) {
                                cacheControl.proxyRevalidate();
                            }
                            // We can customize cache grace period between 0 and 12 hours
                            if (annot.grace() >= 0 && annot.grace() < CacheControlConstants.HEURES_12) {
                                graceValue = annot.grace() + "s";
                            }
                            return CachingInfo.builder().supported(true).cacheControl(cacheControl).graceValue(graceValue).build();
                        }
                    }
                }
            }
            return CachingInfo.builder().supported(false).build();
        });
    }

    @Getter
    @Builder
    @ToString
    private static class CachingInfo {

        private final boolean supported;

        private final CacheControl cacheControl;

        private final String graceValue;

    }

}
