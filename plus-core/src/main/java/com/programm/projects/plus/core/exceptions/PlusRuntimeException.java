package com.programm.projects.plus.core.exceptions;

public class PlusRuntimeException extends RuntimeException{

    public PlusRuntimeException() {
    }

    public PlusRuntimeException(String message) {
        super(message);
    }

    public PlusRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlusRuntimeException(Throwable cause) {
        super(cause);
    }
}
