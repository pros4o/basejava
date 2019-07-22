package com.test.webapp.exception;


public class IOStrategy extends RuntimeException {
    private final String fileName;

    public IOStrategy(String message, String fileName) {
        super(message);
        this.fileName = fileName;
    }

    public IOStrategy(String message, String fileName, Exception e) {
        super(message, e);
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
}
