package com.programm.projects.core.lifecycle;

public interface IChainableLifecycle extends IObservableLifecycle, IChainLifecycle{

    @Override
    default void addLifecycle(ILifecycle lifecycle) {
        addStartupListener(lifecycle::startup);
        addShutdownListener(lifecycle::shutdown);
    }

    @Override
    default void removeLifecycle(ILifecycle lifecycle){
        removeStartupListener(lifecycle::startup);
        removeShutdownListener(lifecycle::startup);
    }
}
