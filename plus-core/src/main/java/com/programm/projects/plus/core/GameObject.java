package com.programm.projects.plus.core;

import com.programm.projects.plus.core.components.Mover;
import com.programm.projects.plus.core.components.Transform;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class GameObject implements IUpdatable, IInitializable{

    public static class GameObjectBuilder {
        private float x, y, sx, sy, r;
        private final Map<Class<? extends IComponent>, IComponent> componentMap = new HashMap<>();
        private final List<IUpdatableComponent> updatableComponents = new ArrayList<>();

        private final List<AbstractComponent> abstractComponents = new ArrayList<>();

        private GameObjectBuilder(){
            this.x = 0;
            this.y = 0;
            this.sx = 1;
            this.sy = 1;
            this.r = 0;
        }

        public GameObjectBuilder setPosition(float x, float y){
            this.x = x;
            this.y = y;
            return this;
        }

        public GameObjectBuilder setScale(float scaleX, float scaleY){
            this.sx = scaleX;
            this.sy = scaleY;
            return this;
        }

        public GameObjectBuilder setRotation(float rotation){
            this.r = rotation;
            return this;
        }

        public GameObjectBuilder add(IComponent component){
            Class<? extends IComponent> cls = component.getClass();
            boolean isUpdatable = component instanceof IUpdatableComponent;

            if(!componentMap.containsKey(cls)) {
                componentMap.put(cls, component);
                if (isUpdatable) {
                    updatableComponents.add((IUpdatableComponent) component);
                }
            }
            else {
                log.warn("Component [{}] is already set!", component.getClass());
            }

            if(component instanceof AbstractComponent){
                abstractComponents.add((AbstractComponent) component);
            }

            return this;
        }

        public GameObject build(){
            Transform transform = new Transform(x, y, sx, sy, r);
            Mover mover = new Mover();

            add(transform);
            add(mover);

            GameObject object = new GameObject(transform, mover, componentMap, updatableComponents);

            abstractComponents.forEach(c -> c.setParent(object));

            return object;
        }

    }

    public static GameObjectBuilder create(){
        return new GameObjectBuilder();
    }





    //STATIC COMPONENTS FOR EVERY OBJECT
    @Getter
    private final Transform transform;
    private final Mover mover;

    private boolean dead;


    private final Map<Class<? extends IComponent>, IComponent> componentMap;
    private final List<IUpdatableComponent> updatableComponents;

    private GameObject(Transform transform, Mover mover, Map<Class<? extends IComponent>, IComponent> componentMap, List<IUpdatableComponent> updatableComponents) {
        this.transform = transform;
        this.mover = mover;
        this.componentMap = componentMap;
        this.updatableComponents = updatableComponents;
    }

    @Override
    public void init(IEngineContext engineContext) {
        for(IComponent component : componentMap.values()) {
            component.init(engineContext);
        }
    }

    @Override
    public void update(IGameContext context) {
        IGameContext newContext = context.createFromNewParent(this);

        for(IUpdatableComponent component : updatableComponents) {
            component.update(newContext);
        }

        newContext.revert();

        mover.update(this);
    }

    @SuppressWarnings("unchecked")
    public <T extends IComponent> T getComponent(Class<T> cls){
        IComponent component = componentMap.get(cls);

        if(component == null) return null;

        return (T)component;
    }

    @SuppressWarnings("unchecked")
    public <T extends IComponent> List<T> getComponentList(Class<T> cls){
        List<T> components = new ArrayList<>();

        for(Class<?> c : componentMap.keySet()){
            if(cls.isAssignableFrom(c)){
                components.add((T)componentMap.get(c));
            }
        }

        return components;
    }

    public void die(){
        dead = true;
    }

    public boolean isDead(){
        return dead;
    }

}
