package com.programm.projects.core.events;

public interface IEventHandler {

    <T extends IEvent> void listenFor(Class<T> cls, EventListener<T> listener);
    <T extends IEvent> void dispatchEvent(T event);

}
