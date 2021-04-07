package com.programm.projects.plus.core.events;

public interface IEventHandler extends IEventDispatcher{

    <T extends IEvent> void listenFor(Class<T> cls, EventListener<T> listener);

}
