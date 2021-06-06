package com.programm.projects.plus.core;

import com.programm.projects.plus.core.lifecycle.ILifecycle;

public interface ISubsystem extends ILifecycle {

    boolean setEnabled(boolean enabled);

    default boolean enable(){
        return setEnabled(true);
    }

    default boolean disable(){
        return setEnabled(false);
    }

}
