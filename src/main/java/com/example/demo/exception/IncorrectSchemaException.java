package com.example.demo.exception;

public class IncorrectSchemaException extends RuntimeException {
    public IncorrectSchemaException() {
        super();
    }

    public IncorrectSchemaException(String message) {
        super(message);
    }

    public IncorrectSchemaException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectSchemaException(Throwable cause) {
        super(cause);
    }

    protected IncorrectSchemaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
