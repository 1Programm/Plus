package com.programm.projects.core;

import java.util.ArrayList;
import java.util.List;

class ComponentList implements IComponent{

    private final List<IComponent> components = new ArrayList<>();

    public void add(IComponent component){
        components.add(component);
    }

    @Override
    public void init() {
        for(IComponent c : components) {
            c.init();
        }
    }

    @Override
    public void update(IGameContext context) {
        for(IComponent c : components) {
            c.update(context);
        }
    }
}
