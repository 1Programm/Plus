package com.programm.projects.plus.engine.api.exceptions;

public class EngineRuntimeException extends RuntimeException{

    public EngineRuntimeException() {
    }

    public EngineRuntimeException(String message) {
        super(message);
    }

    public EngineRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EngineRuntimeException(Throwable cause) {
        super(cause);
    }
}
