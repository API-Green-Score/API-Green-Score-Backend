package fr.pagesjaunes.socletechnique.lang.thread;

import fr.pagesjaunes.socletechnique.lang.io.ByteArrayOutputStreamWrapper;
import fr.pagesjaunes.socletechnique.lang.io.FastByteArrayOutputStream;
import fr.pagesjaunes.socletechnique.lang.io.FastByteArrayOutputStreamWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;

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
        } else if (value instanceof FastByteArrayOutputStream buf) {
            if (buf.getSize() < maxBufferSize) {
                buf.reset();
            } else {
                value = wrap(initialValue());
            }
        } else if (value instanceof ByteArrayOutputStream buf) {
            if (buf.size() < maxBufferSize) {
                buf.reset();
            } else {
                value = wrap(initialValue());
            }
        } else if (value instanceof CharArrayWriter buf) {
            if (buf.size() < maxBufferSize) {
                buf.reset();
            } else {
                value = initialValue();
                set(value);
            }
        } else {
            throw new IllegalArgumentException("Unknown value type");
        }

        return value;
    }

    @SuppressWarnings("unchecked")
    private T wrap(final T pValue) {
        T value = pValue;
        if ((pValue instanceof FastByteArrayOutputStream) && !(pValue instanceof FastByteArrayOutputStreamWrapper)) {
            value = (T) new FastByteArrayOutputStreamWrapper((FastByteArrayOutputStream) pValue, maxBufferSize);
            set(value);
        } else if ((pValue instanceof ByteArrayOutputStream) && !(pValue instanceof ByteArrayOutputStreamWrapper)) {
            value = (T) new ByteArrayOutputStreamWrapper((ByteArrayOutputStream) pValue, maxBufferSize);
            set(value);
        }
        return value;
    }

    /**
     * @param pMaxBufferSize the maxBufferSize to set
     */
    public InitializableThreadLocal<T> setMaxBufferSize(final int pMaxBufferSize) {
        this.maxBufferSize = pMaxBufferSize;
        return this;
    }
}
