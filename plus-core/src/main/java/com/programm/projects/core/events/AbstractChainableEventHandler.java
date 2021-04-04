package com.programm.projects.core.events;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractChainableEventHandler implements IChainableEventHandler{

    private final List<IEventHandler> eventHandlers = new ArrayList<>();

    protected abstract <T extends IEvent> void onDispatchEvent(T event);

    @Override
    public final <T extends IEvent> void dispatchEvent(T event) {
        if(event.handle()){
            onDispatchEvent(event);
        }

        eventHandlers.forEach(h -> h.dispatchEvent(event));
    }

    @Override
    public void addHandler(IEventHandler handler) {
        eventHandlers.add(handler);
    }

    @Override
    public void removeHandler(IEventHandler handler) {
        eventHandlers.remove(handler);
    }

}
