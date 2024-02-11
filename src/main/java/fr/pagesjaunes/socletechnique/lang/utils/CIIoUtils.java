package fr.pagesjaunes.socletechnique.lang.utils;

import fr.pagesjaunes.socletechnique.lang.thread.InitializableThreadLocal;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 *
 */
public final class CIIoUtils {

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    private static final InitializableThreadLocal<StringBuilder> TL_SB = new InitializableThreadLocal<>(StringBuilder.class);

    private static final ThreadLocal<char[]> TL_CHAR_BUFFER = ThreadLocal.withInitial(() -> new char[DEFAULT_BUFFER_SIZE]);

    private static final ThreadLocal<byte[]> TL_BYTE_BUFFER = ThreadLocal.withInitial(() -> new byte[DEFAULT_BUFFER_SIZE]);

    /**
     * Constructeur.
     */
    private CIIoUtils() {
        // noop
    }

    public static String toString(final InputStream in) throws IOException {

        return toString(in, StandardCharsets.UTF_8);
    }

    public static String toString(final InputStream in, final Charset charset) throws IOException {
        InputStreamReader reader = new InputStreamReader(in, charset);
        char[] charBuffer = TL_CHAR_BUFFER.get();
        StringBuilder buffer = TL_SB.getCleanValue();
        int read = reader.read(charBuffer);
        while (read != -1) {
            buffer.append(charBuffer, 0, read);
            read = reader.read(charBuffer);
        }
        return buffer.toString();
    }

    public static String toString(final InputStream in, final String charset) throws IOException {
        return toString(in, CICharsetUtils.getCharset(charset));
    }

    /**
     * @param pInputStream
     * @param pOutputStream
     * @throws IOException
     */
    public static void copy(final InputStream pInputStream, final OutputStream pOutputStream) throws IOException {
        byte[] byteBuffer = TL_BYTE_BUFFER.get();
        int read = pInputStream.read(byteBuffer);
        while (read != -1) {
            pOutputStream.write(byteBuffer, 0, read);
            read = pInputStream.read(byteBuffer);
        }
    }

}
