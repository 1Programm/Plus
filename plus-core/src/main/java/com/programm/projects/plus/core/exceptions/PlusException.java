package com.programm.projects.plus.core.exceptions;

public class PlusException extends Exception{

    public PlusException() {
    }

    public PlusException(String message) {
        super(message);
    }

    public PlusException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlusException(Throwable cause) {
        super(cause);
    }
}
