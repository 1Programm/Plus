package com.programm.projects.core.events;

public interface IEvent {

    /**
     *
     * @return true if the event should be handled
     */
    default boolean handle() { return true; }

    default void consume() { }

}
