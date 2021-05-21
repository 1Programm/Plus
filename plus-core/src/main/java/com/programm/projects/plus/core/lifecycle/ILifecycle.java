package com.programm.projects.plus.core.lifecycle;

import com.programm.projects.plus.core.exceptions.PlusException;

public interface ILifecycle {

    void startup() throws PlusException;
    void shutdown() throws PlusException;

}
