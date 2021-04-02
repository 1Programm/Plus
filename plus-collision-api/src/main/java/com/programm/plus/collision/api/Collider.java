package com.programm.plus.collision.api;

import com.programm.plus.core.component.Component;
import com.programm.plus.core.obj.GameObject;
import com.programm.plus.core.component.ExtraComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collider extends ExtraComponent {

    private List<CollisionListener> listeners;

    public Collider() {
        this.listeners = new ArrayList<>();
    }

    public Collider(CollisionListener... listeners){
        this.listeners = new ArrayList<>();
        if(listeners != null) {
            this.listeners.addAll(Arrays.asList(listeners));
        }
    }

    Collider(List<CollisionListener> listeners) {
        this.listeners = listeners;
    }

    public void onCollision(GameObject other){
        listeners.forEach(l -> l.onCollision(gameObject, other));
    }

    public Collider addListener(CollisionListener listener){
        this.listeners.add(listener);

        return this;
    }

    public boolean removeListener(CollisionListener listener){
        return this.listeners.remove(listener);
    }

    @Override
    public Component copy() {
        return new Collider(listeners);
    }
}
