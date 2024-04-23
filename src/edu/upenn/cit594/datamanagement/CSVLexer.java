package edu.upenn.cit594.datamanagement;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CSVLexer {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 5130409650040L;
    private final CharacterReader reader;
    private final StringBuilder currString = new StringBuilder();
    private int currLine = 0;

    public enum State {
        START,
        FIELD,
        ESCAPED,
        NON_ESCAPED,
        CRLF,
        COMMA,
        ESCAPE_PENDING,
        EOF,
        LF,
        EXCEPTION
    }

    public CSVLexer(CharacterReader reader) {
        this.reader = reader;
    }

    public Optional<Character> readChar() throws IOException {
        int c = reader.read();

        // empty means EOF
        return c == -1 ? Optional.empty() : Optional.of((char) c);
    }

    public void addRowAndReset(List<String> row) {
        row.add(currString.toString());
        currString.setLength(0);
    }

    /**
     * This method uses the class's {@code CharacterReader} to read in just enough
     * characters to process a single valid CSV row, represented as an array of
     * strings where each element of the array is a field of the row. If formatting
     * errors are encountered during reading, this method throws a
     * {@code CSVFormatException} that specifies the exact point at which the error
     * occurred.
     *
     * @return a single row of CSV represented as a string array, where each
     *         element of the array is a field of the row; or {@code null} when
     *         there are no more rows left to be read.
     * @throws IOException when the underlying reader encountered an error
     * @throws CSVFormatException when the CSV file is formatted incorrectly
     */

    public String[] readRow() throws IOException, CSVFormatException {
        List<String> row = new ArrayList<String>();
        Optional<Character> c;
        State state = State.START;
        while (true) {
            switch(state) {
                case START:
                    state = State.FIELD;
                case FIELD:
                    c = readChar();
                    if (c.isEmpty()) {
                        if (row.isEmpty()) return null;
                        state = State.EOF;
                    } else if (c.get() == '"') {
                        state = State.ESCAPED;
                    } else if (c.get() == '\r') {
                        state = State.CRLF;
                    } else if (c.get() == '\n') {
                        addRowAndReset(row);
                        state = State.LF;
                    } else if (c.get() == ',') {
                        addRowAndReset(row);
                        state = State.COMMA;
                    } else {
                        currString.append(c.get());
                        state = State.NON_ESCAPED;
                    }
                    break;
                case ESCAPED:
                    c = readChar();
                    if (c.isEmpty()) {
                        state = State.EXCEPTION;
                    } else if (c.get() == '"') {
                        state = State.ESCAPE_PENDING;
                    } else {
                        currString.append(c.get());
                        state = State.ESCAPED;
                    }
                    break;
                case NON_ESCAPED:
                    c = readChar();
                    if (c.isEmpty()) {
                        addRowAndReset(row);
                        state = State.EOF;
                    }else if (c.get() == '"') {
                        state = State.EXCEPTION;
                    } else if (c.get() == '\r') {
                        state = State.CRLF;
                    } else if (c.get() == '\n') {
                        addRowAndReset(row);
                        state = State.LF;
                    } else if (c.get() == ',') {
                        addRowAndReset(row);
                        state = State.COMMA;
                    } else {
                        currString.append(c.get());
                        state = State.NON_ESCAPED;
                    }
                    break;
                case ESCAPE_PENDING:
                    c = readChar();
                    if (c.isEmpty()) {
                        addRowAndReset(row);
                        state = State.EOF;
                    } else if (c.get() == '"') {
                        currString.append('\"');
                        state = State.ESCAPED;
                    } else if (c.get() == '\r') {
                        state = State.CRLF;
                    } else if (c.get() == '\n') {
                        addRowAndReset(row);
                        state = State.LF;
                    } else if (c.get() == ',') {
                        addRowAndReset(row);
                        state = State.COMMA;
                    } else {
                        state = State.EXCEPTION;
                    }
                    break;
                case CRLF:
                    c = readChar();
                    if (c.get() == '\n') {
                        addRowAndReset(row);
                        state = State.LF;
                    } else {
                        state = State.EXCEPTION;
                    }
                    break;
                case LF:
                    return row.toArray(new String[0]);
                case COMMA:
                    state = State.FIELD;
                    break;
                case EXCEPTION:
                    throw new CSVFormatException();
                case EOF:
                    return row.toArray(new String[0]);
            }
        }
    }
}