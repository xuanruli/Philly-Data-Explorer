package datamanagement;

import logging.Logger;

import java.io.File;

public abstract class DataReader<T> {
    protected File file;

    public DataReader(File file) {
        this.file = file;
    }

    public T read() throws Exception {
        if (file == null || !file.exists()) {
            throw new Exception("File does not exist.");
        }

        Logger logger = Logger.getInstance();
        logger.logEvent("[READ] " + file.getName());
        return getDataFromFile();
    }

    protected abstract T getDataFromFile() throws Exception;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
