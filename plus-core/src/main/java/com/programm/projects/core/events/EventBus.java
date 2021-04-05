package com.programm.projects.core.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus extends AbstractChainableEventHandler{

    private final Map<Class<? extends IEvent>, List<EventListener<? extends IEvent>>> eventListenerMap = new HashMap<>();

    @Override
    public <T extends IEvent> void listenFor(Class<T> cls, EventListener<T> listener){
        eventListenerMap.computeIfAbsent(cls, c -> new ArrayList<>()).add(listener);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IEvent> void onDispatchEvent(T event){
        handleEvent(event, event.getClass());
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

}
