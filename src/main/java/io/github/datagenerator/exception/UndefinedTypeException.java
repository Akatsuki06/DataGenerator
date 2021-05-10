package io.github.datagenerator.exception;

public class UndefinedTypeException extends Exception{

    public UndefinedTypeException() {
        super();
    }

    public UndefinedTypeException(String message) {
        super(message);
    }

    public UndefinedTypeException(String message, Throwable cause) {
        super(message, cause);
    }


}
