package fr.apithinking.apigreenscore.corelang.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author srouthiau
 */
@Slf4j
public class FastByteArrayOutputStreamWrapper extends FastByteArrayOutputStream {

    private final int maxSizeOnClose;

    /**
     * @param wrapped
     * @param maxSizeOnClose
     */
    public FastByteArrayOutputStreamWrapper(final FastByteArrayOutputStream wrapped, final int maxSizeOnClose) {
        this.maxSizeOnClose = maxSizeOnClose;
        this.buf = wrapped.getByteArray();
        this.size = wrapped.size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        super.close();
        if (buf.length > maxSizeOnClose) {
            LOGGER.debug("Appel de la methode close avec une longueur superieur a {}", maxSizeOnClose);

            buf = new byte[maxSizeOnClose / 2];
            size = 0;
        }
    }
}
