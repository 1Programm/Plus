package com.programm.projects.plus.core.lifecycle;

public interface IChainLifecycle extends ILifecycle{

    void addLifecycle(ILifecycle lifecycle);
    void removeLifecycle(ILifecycle lifecycle);

}
