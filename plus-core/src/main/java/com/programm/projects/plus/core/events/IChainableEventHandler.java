package com.programm.projects.plus.core.events;

public interface IChainableEventHandler extends IEventHandler{

    void addHandler(IEventHandler handler);
    void removeHandler(IEventHandler handler);

}
