package com.programm.projects.core.events;

public class ConsumableEvent implements IEvent{

    private boolean consumed;

    @Override
    public boolean handle() {
        return !consumed;
    }

    @Override
    public void consume() {
        consumed = true;
    }
}
