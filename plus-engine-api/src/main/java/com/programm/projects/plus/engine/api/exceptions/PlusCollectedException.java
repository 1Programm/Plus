package com.programm.projects.plus.engine.api.exceptions;

import com.programm.projects.plus.core.exceptions.PlusException;

public class PlusCollectedException extends PlusException {

    public PlusCollectedException() {
    }

    public PlusCollectedException(String message) {
        super(message);
    }

    public PlusCollectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlusCollectedException(Throwable cause) {
        super(cause);
    }
}
