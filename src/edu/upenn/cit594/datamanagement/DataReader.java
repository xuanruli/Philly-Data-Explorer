package edu.upenn.cit594.datamanagement;

import java.io.File;

public abstract class DataReader<T> {
    protected File file;

    public DataReader(File file) {
        this.file = file;
    }

    public T read() throws Exception {
        if (file == null || !file.exists()) {
            System.err.println("File does not exist.");
        }

        return getDataFromFile();
    }

    public abstract T getDataFromFile() throws Exception;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
