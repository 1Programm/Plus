package com.programm.projects.plus.core.lifecycle;

public interface IChainableLifecycle extends IObservableLifecycle, IChainLifecycle{

    @Override
    default void addLifecycle(ILifecycle lifecycle) {
        if(lifecycle == null) return;
        addStartupListener(lifecycle::startup);
        addShutdownListener(lifecycle::shutdown);
    }

    @Override
    default void removeLifecycle(ILifecycle lifecycle){
        if(lifecycle == null) return;
        removeStartupListener(lifecycle::startup);
        removeShutdownListener(lifecycle::startup);
    }
}
