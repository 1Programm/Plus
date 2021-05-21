package com.programm.projects.plus.core.exceptions;

public class PlusFatalException extends PlusException{

    public PlusFatalException() {
    }

    public PlusFatalException(String message) {
        super(message);
    }

    public PlusFatalException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlusFatalException(Throwable cause) {
        super(cause);
    }
}
