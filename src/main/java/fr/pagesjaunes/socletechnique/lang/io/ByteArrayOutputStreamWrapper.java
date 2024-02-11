package fr.pagesjaunes.socletechnique.lang.io;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author srouthiau
 */
@Slf4j
public class ByteArrayOutputStreamWrapper extends ByteArrayOutputStream {

    private final int maxSizeOnClose;

    /**
     * @param wrapped
     * @param maxSizeOnClose
     */
    public ByteArrayOutputStreamWrapper(final ByteArrayOutputStream wrapped, final int maxSizeOnClose) {
        this.maxSizeOnClose = maxSizeOnClose;
        this.buf = wrapped.toByteArray();
        this.count = wrapped.size();
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
            count = 0;
        }
    }
}
