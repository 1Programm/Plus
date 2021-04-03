package com.programm.projects.core.lifecycle;

public interface IChainLifecycle extends ILifecycle{

    void addToChain(ILifecycle lifecycle);

}
