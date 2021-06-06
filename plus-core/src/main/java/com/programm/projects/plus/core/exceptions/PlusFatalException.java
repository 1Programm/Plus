package com.programm.projects.plus.core.exceptions;

import java.util.function.Supplier;

public class PlusFatalException extends PlusException{

    public static Supplier<PlusFatalException> Supply(String message){
        return () -> new PlusFatalException(message);
    }

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
