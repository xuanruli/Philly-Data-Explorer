package edu.upenn.cit594.datamanagement;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * {@code CharacterReader} is a wrapper for normal {@code Reader} and
 * {@code InputStream} objects to restrict processing to a single character at
 * a time. It is only intended for educational purposes and is generally
 * unnecessary for ordinary applications.
 */
public class CharacterReader implements AutoCloseable {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1160622270049L;
    private final BufferedReader reader;

    public CharacterReader(String filename) throws IOException {
        this.reader = Files.newBufferedReader(Paths.get(filename));
    }

    public CharacterReader(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    public CharacterReader(InputStream inputStream) {
        this(new InputStreamReader(inputStream));
    }

    /**
     * Reads a single character, or -1 on EOF.
     *
     * @return The character read, as an integer in the range 0 to 65535
     *         ({@code0x00-0xffff}), or -1 if the end of the stream has
     *         been reached
     * @throws IOException If an I/O error occurs
     */
    public int read() throws IOException {
        return this.reader.read();
    }

    /**
     * Closes the stream and releases any system resources associated with it.
     * Once the stream has been closed, further read(), ready(), mark(),
     * reset(), or skip() invocations will throw an {@code IOException}.
     * Closing a previously closed stream has no effect.
     *
     * <p>Since {@code CharacterReader} implements {@code AutoCloseable}, this
     * method will be automatically invoked if the {@code CharacterReader}
     * is instantiated using try-with-resources.
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}
