package com.programm.plus.core.obj;

import com.programm.plus.core.component.*;
import com.programm.plus.goh.api.GameObjectInitializer;
import com.programm.plus.maths.Transform2f;
import com.programm.plus.maths.Vector2f;
import com.programm.plus.tags.Tag;
import com.programm.plus.tags.TagHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
public class GameObjectBuilder {

    static Mover addComponent(Component component,
                              Map<Class<? extends ExtraComponent>, ExtraComponent> extraComponents,
                              Map<Class<? extends UpdateComponent>, UpdateComponent> updateComponents){
        Mover mover = null;

        if(component instanceof Mover) {
            mover = (Mover)component;
        }
        else if(component instanceof ExtraComponent){
            ExtraComponent eComponent = (ExtraComponent)component;

            Class<? extends ExtraComponent> cls = eComponent.getClass();

            if(extraComponents.containsKey(cls)){
                log.warn("ExtraComponent '{}' already registered.", cls.getSimpleName());
            }
            else {
                extraComponents.put(cls, eComponent);
            }
        }
        else if(component instanceof UpdateComponent){
            UpdateComponent uComponent = (UpdateComponent)component;

            Class<? extends UpdateComponent> cls = uComponent.getClass();

            if(updateComponents.containsKey(cls)){
                log.warn("UpdateComponent '{}' already registered.", cls.getSimpleName());
            }
            else {
                updateComponents.put(cls, uComponent);
            }
        }
        else {
            log.warn("Component {} is no valid component.", component);
        }

        return mover;
    }

    private Consumer<GameObject> addGameObject;
    private GameObjectInitializer initGameObject;

    private Transform2f transformation = new Transform2f();
    private Mover mover;
    private Map<Class<? extends ExtraComponent>, ExtraComponent> extraComponents = new HashMap<>();
    private Map<Class<? extends UpdateComponent>, UpdateComponent> updateComponents = new HashMap<>();
    private TagHandler tags = new TagHandler();

    public GameObjectBuilder(Consumer<GameObject> addGameObject, GameObjectInitializer initGameObject){
        this.addGameObject = addGameObject;
        this.initGameObject = initGameObject;
    }

    public GameObjectBuilder(Consumer<GameObject> addGameObject, GameObjectInitializer initGameObject, ObjectInstance instance){
        this.addGameObject = addGameObject;
        this.initGameObject = initGameObject;
        this.transformation = instance.transformation.clone();
        this.mover = instance.mover == null ? null : instance.mover.copy();

        this.extraComponents = new HashMap<>();
        instance.extraComponents.forEach((cls, cmp) -> {
            ExtraComponent _cmp = (ExtraComponent)cmp.copy();
            this.extraComponents.put(cls, _cmp);
        });

        this.updateComponents = new HashMap<>();
        instance.updateComponents.forEach((cls, cmp) -> {
            UpdateComponent _cmp = (UpdateComponent)cmp.copy();
            this.updateComponents.put(cls, _cmp);
        });

        this.tags = instance.tags.clone();
    }

    public GameObjectBuilder setPosition(Vector2f position){
        this.transformation.setPosition(position);
        return this;
    }

    public GameObjectBuilder setPosition(float x, float y){
        this.transformation.setPosition(x, y);
        return this;
    }

    public GameObjectBuilder setSize(Vector2f size){
        this.transformation.setSize(size);
        return this;
    }

    public GameObjectBuilder setSize(float width, float height){
        this.transformation.setSize(width, height);
        return this;
    }

    public GameObjectBuilder setRotation(float rotation){
        this.transformation.setRotation(rotation);
        return this;
    }

    public GameObjectBuilder addComponent(Component component){
        Mover mover = addComponent(component, extraComponents, updateComponents);

        if(mover != null) {
            if (this.mover != null) {
                log.warn("Overwriting existing Mover component");
            }
            this.mover = mover;
        }

        return this;
    }

    public GameObjectBuilder addTag(Tag tag){
        this.tags.tag(tag);
        return this;
    }

    public GameObject build(){
        initGameObject.accept(tags.getTags(), mover, extraComponents, updateComponents, this::addComponent);

        ComponentHandler components = new ComponentHandler(mover, extraComponents, updateComponents);

        GameObject obj = new GameObject(transformation, components, tags);

        components.init(obj);

        addGameObject.accept(obj);

        return obj;
    }

}
