package com.programm.projects.plus.engine.api.exceptions;

import com.programm.projects.plus.core.exceptions.PlusException;

public interface IExceptionHandler {

    IExceptionHandler PASS = e -> { throw e; };


    void handle(PlusException exception) throws PlusException;

}
