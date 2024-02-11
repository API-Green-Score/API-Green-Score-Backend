package fr.pagesjaunes.socletechnique.lang.thread;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.ConstructorUtils;

/**
 * Initialisation d'un threadlocal.
 *
 * @param <T>
 * @author lpouzac
 */
@Slf4j
public class InitializableThreadLocal<T> extends ThreadLocal<T> {

    private static final int DEFAULT_MAX_BUFFER_SIZE = 1024 * 512;

    private final Object[] args;

    private final Class<T> clz;

    private int maxBufferSize = DEFAULT_MAX_BUFFER_SIZE;

    /**
     * @param clz
     * @param args
     */
    public InitializableThreadLocal(final Class<T> clz, final Object... args) {
        this.args = args;
        this.clz = clz;
    }

    @Override
    protected T initialValue() {
        T object = null;
        try {
            object = ConstructorUtils.invokeConstructor(clz, args);
        } catch (Exception e) {
            LOGGER.warn("L'invocation du constructeur a echoue : {}", e.getMessage());
        }
        return object;
    }

    @SuppressWarnings("resource")
    public T getCleanValue() {
        T value = get();
        if (value instanceof StringBuilder buf) {
            if (buf.length() > maxBufferSize) {
                buf.setLength(maxBufferSize);
                buf.trimToSize();
            }
            buf.setLength(0);
        } else {
            throw new IllegalArgumentException("Unknown value type");
        }

        return value;
    }

    @SuppressWarnings("unchecked")
    private T wrap(final T pValue) {
        return pValue;
    }

}
