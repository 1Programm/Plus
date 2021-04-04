package com.programm.projects.core.events;

public interface EventListener<T extends IEvent> {

    void onEvent(T event);

}
