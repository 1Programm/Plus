package com.programm.projects.core.lifecycle;

public interface IChainableLifecycle extends IObservableLifecycle, IChainLifecycle{

    @Override
    default void addToChain(ILifecycle lifecycle) {
        addStartupListener(lifecycle::startup);
        addShutdownListener(lifecycle::shutdown);
    }
}
