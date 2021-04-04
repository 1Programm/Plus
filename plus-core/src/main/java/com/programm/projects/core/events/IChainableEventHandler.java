package com.programm.projects.core.events;

public interface IChainableEventHandler extends IEventHandler{

    void addHandler(IEventHandler handler);
    void removeHandler(IEventHandler handler);

}
