package com.programm.projects.plus.core.events;

public interface EventListener<T extends IEvent> {

    void onEvent(T event);

}
