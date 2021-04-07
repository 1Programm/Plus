package com.programm.projects.plus.engine.api.events;

import com.programm.projects.plus.core.events.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventStack implements IChainableEventHandler {

    private final List<IEventHandler> eventHandlers = new ArrayList<>();
    private final Map<Class<? extends IEvent>, List<EventListener<? extends IEvent>>> eventListenerMap = new HashMap<>();

    private final List<IEvent> stackA = new ArrayList<>();
    private final List<IEvent> stackB = new ArrayList<>();
    private List<IEvent> currentStack = stackA;


    @Override
    public <T extends IEvent> void listenFor(Class<T> cls, EventListener<T> listener){
        eventListenerMap.computeIfAbsent(cls, c -> new ArrayList<>()).add(listener);
    }

    @Override
    public <T extends IEvent> void dispatch(T event){
        if(!event.handle()) return;

        if(event.immediate()){
            handle(event);
        }
        else {
            currentStack.add(event);
        }
    }

    private void handle(IEvent event){
        handleEvent(event, event.getClass());

        for(IEventHandler eventHandler : eventHandlers){
            eventHandler.dispatch(event);
        }
    }

    public void pollEvents(){
        List<IEvent> offlineStack = currentStack;
        currentStack = currentStack == stackA ? stackB : stackA;

        for(IEvent event : offlineStack){
            handle(event);
        }

        offlineStack.clear();
    }

    @SuppressWarnings("unchecked")
    private <T extends IEvent> void handleEvent(T event, Class<? extends IEvent> cls){
        List<EventListener<? extends IEvent>> list = eventListenerMap.get(cls);

        if(list != null) {
            List<EventListener<T>> batch = (List<EventListener<T>>) (List<?>) list;

            batch.forEach(l -> {
                if (event.handle()) {
                    l.onEvent(event);
                }
            });
        }

        Class<?> superCls = cls.getSuperclass();
        if(IEvent.class.isAssignableFrom(superCls)){
            Class<? extends IEvent> superEvtCls = (Class<? extends IEvent>) superCls;
            handleEvent(superEvtCls.cast(event), superEvtCls);
        }
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
