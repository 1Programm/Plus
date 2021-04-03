package com.programm.projects.core;

import com.programm.projects.plus.maths.Vector2f;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
public class GameObject implements IUpdatable, IInitializable{

    public static class GameObjectBuilder {
        private final GameObject object;

        private GameObjectBuilder(){
            this.object = new GameObject();
        }

        public GameObjectBuilder setPosition(float x, float y){
            this.object.position.set(x, y);
            return this;
        }

        public GameObjectBuilder setSize(float w, float h){
            this.object.size.set(w, h);
            return this;
        }

        public GameObjectBuilder setRotation(float rotation){
            this.object.rotation = rotation;
            return this;
        }

        public GameObjectBuilder add(IComponent component){
            Class<? extends IComponent> cls = component.getClass();

            if(!object.componentMap.containsKey(cls)){
                object.componentMap.put(cls, component);
            }
            else {
                if(!component.canHaveMultiple()){
                    log.warn("Object can not have multiple components of class [{}]!", cls);
                    return this;
                }

                IComponent c = object.componentMap.get(cls);

                if(c instanceof ComponentList){
                    ComponentList componentList = (ComponentList) c;
                    componentList.add(component);
                }
                else {
                    ComponentList componentList = new ComponentList();
                    componentList.add(c);
                    componentList.add(component);
                    object.componentMap.put(cls, componentList);
                }
            }

            return this;
        }

        public GameObject build(){
            return object;
        }

    }

    public static GameObjectBuilder create(){
        return new GameObjectBuilder();
    }






    private boolean initialized;

    private final Vector2f position = new Vector2f();
    private final Vector2f size = new Vector2f();
    private float rotation;

    private final Map<Class<? extends IComponent>, IComponent> componentMap = new HashMap<>();

    private GameObject(){}

    @Override
    public void init() {
        initialized = true;
        for(IComponent component : componentMap.values()) {
            component.init();
        }
    }

    @Override
    public void update() {
        for(IComponent component : componentMap.values()) {
            component.update();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends IComponent> T getComponent(Class<T> cls){
        IComponent component = componentMap.get(cls);

        if(component == null) return null;

        return (T)component;
    }

}
