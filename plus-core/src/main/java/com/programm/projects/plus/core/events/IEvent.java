package com.programm.projects.plus.core.events;

public interface IEvent {

    /**
     *
     * @return true if the event should be handled
     */
    default boolean handle() { return true; }

    /**
     * Mark this event as consumed.
     * If this event can be consumed {@link IEvent#handle()} should return false.
     */
    default void consume() { }

}
