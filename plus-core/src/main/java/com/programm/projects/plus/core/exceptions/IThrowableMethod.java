package com.programm.projects.plus.core.exceptions;

public interface IThrowableMethod {

    static <T extends PlusException> IThrowableMethod Throw(T ex){
        return () -> { throw ex; };
    }

    void run() throws PlusException;

}
