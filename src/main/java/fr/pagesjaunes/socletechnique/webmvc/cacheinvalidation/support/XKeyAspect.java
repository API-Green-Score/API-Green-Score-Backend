package fr.pagesjaunes.socletechnique.webmvc.cacheinvalidation.support;

import fr.pagesjaunes.socletechnique.core.support.HeadersStorage;
import fr.pagesjaunes.socletechnique.webmvc.cacheinvalidation.annotation.XKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.lang.NonNull;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * AOP aspect that handles xkey header (for cache invalidation), based on the
 * {@link XKey} annotation. A 'xkey' header will be generated in the response
 * for methods of (Rest controller) annotated with the annotation.
 */
@Aspect
@Slf4j
public class XKeyAspect {

    private static final SpelExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    private final EvaluationContext evaluationContext = SimpleEvaluationContext.forReadOnlyDataBinding().build();

    @Autowired
    @Qualifier(HeadersStorage.RESPONSE_HEADERS_TO_AGGREGATE_STORAGE_BEAN_NAME)
    private HeadersStorage xkeyHeadersStorage;

    public XKeyAspect() throws NoSuchMethodException {
        evaluationContext.setVariable("format", ContextFunctions.class.getDeclaredMethod("format", String.class, Object[].class));
    }

    public XKeyAspect(HeadersStorage storage) throws NoSuchMethodException {
        this();
        xkeyHeadersStorage = storage;
    }


    private final ConcurrentHashMap<String, XKeysGenerationInfos> xkeyGenerationInfos = new ConcurrentHashMap<>();

    @Around("@annotation(fr.pagesjaunes.socletechnique.webmvc.cacheinvalidation.annotation.XKey)")
    public Object process(ProceedingJoinPoint pjp) throws Throwable {
        Object returnValue = pjp.proceed();
        Object entity = returnValue;
        if (entity != null && Optional.class.isAssignableFrom(entity.getClass())) {
            entity = ((Optional<Object>) entity).orElse(null);
        }
        if (entity != null) {
            XKeysGenerationInfos infos = getXKeyGenerationInfos(pjp.getSignature());
            if (infos.isSupported()) {
                RootEvaluationContextObject rec = new RootEvaluationContextObject(entity);
                List<String> xkeys = infos.getXkeys().stream().map(xk -> xk.getExpressionValues(evaluationContext, rec)).reduce(new ArrayList<>(), (l, r) -> {
                    l.addAll(r);
                    return l;
                });
                xkeys.forEach(v -> xkeyHeadersStorage.add(XKeyConstants.XKEY_HEADER_NAME, v));
            }

        }
        return returnValue;
    }

    private @NonNull
    XKeysGenerationInfos getXKeyGenerationInfos(Signature signature) {
        return xkeyGenerationInfos.computeIfAbsent(signature.toLongString(), k -> {
            if (MethodSignature.class.isAssignableFrom(signature.getClass())) {
                MethodSignature ms = (MethodSignature) signature;
                if (ms.getMethod() != null) {
                    XKey[] annots = ms.getMethod().getAnnotationsByType(XKey.class);
                    if (annots.length > 0) {
                        try {
                            return XKeysGenerationInfos.builder().supported(true).xkeys(
                                    Arrays
                                            .stream(annots)
                                            .map(a -> XKeyGenerationInfos.builder()
                                                    .dataType(a.dataType())
                                                    .expression(EXPRESSION_PARSER.parseExpression(a.dataIdExpression()))
                                                    .build())
                                            .collect(Collectors.toList())
                            ).build();
                        } catch (SpelParseException e) {
                            LOGGER.error("Invalid xkey expression (parsing error)", e);
                            return XKeysGenerationInfos.builder().supported(false).build();
                        }
                    }
                }
            }
            return XKeysGenerationInfos.builder().supported(false).build();
        });
    }

    @Data
    @Builder
    private static class XKeysGenerationInfos {

        private boolean supported;

        private List<XKeyGenerationInfos> xkeys;

    }

    @Data
    @Builder
    @Slf4j
    private static class XKeyGenerationInfos {

        private String dataType;

        private Expression expression;

        @SuppressWarnings("unchecked")
        public List<String> getExpressionValues(EvaluationContext evaluationContext, RootEvaluationContextObject rootEvaluationContextObject) {
            try {
                List<Object> values = expression.getValue(evaluationContext, rootEvaluationContextObject, List.class);
                if (values != null) {
                    return values.stream().map(v -> dataType + "-" + v.toString()).toList();
                }
                return Collections.emptyList();
            } catch (Exception e) {
                LOGGER.error("Error while evaluating xkey expression", e);
                return Collections.emptyList();
            }
        }

    }

    @Getter
    @AllArgsConstructor
    private static class RootEvaluationContextObject {

        private final Object entity;

    }

    private static class ContextFunctions {

        public static Collection<String> format(String format, Object... values) {
            return Arrays.stream(values).map(v -> MessageFormat.format(format, v)).toList();
        }
    }
}
