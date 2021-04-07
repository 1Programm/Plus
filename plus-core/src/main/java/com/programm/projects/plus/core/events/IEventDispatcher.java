package com.programm.projects.plus.core.events;

public interface IEventDispatcher {

    <T extends IEvent> void dispatch(T event);

}
