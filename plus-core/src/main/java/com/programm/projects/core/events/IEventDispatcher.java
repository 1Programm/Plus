package com.programm.projects.core.events;

public interface IEventDispatcher {

    <T extends IEvent> void dispatch(T event);

}
