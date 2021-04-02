package com.programm.plus.core.obj;

import com.programm.plus.core.component.*;
import com.programm.plus.maths.Transform2f;
import com.programm.plus.maths.Vector2f;
import com.programm.plus.tags.Tag;
import com.programm.plus.tags.TagHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ObjectInstance {

    Transform2f transformation = new Transform2f();
    Mover mover = null;
    Map<Class<? extends ExtraComponent>, ExtraComponent> extraComponents = new HashMap<>();
    Map<Class<? extends UpdateComponent>, UpdateComponent> updateComponents = new HashMap<>();
    TagHandler tags = new TagHandler();

    protected void setPosition(float x, float y){
        this.transformation.getPosition().set(x, y);
    }

    protected void setPosition(Vector2f position){
        this.transformation.setPosition(position);
    }

    protected void setSize(float width, float height){
        this.transformation.getSize().set(width, height);
    }

    protected void setSize(Vector2f size){
        this.transformation.setSize(size);
    }

    protected void setRotation(float rotation){
        this.transformation.setRotation(rotation);
    }

    protected void addComponent(Component component){
        Mover mover = GameObjectBuilder.addComponent(component, extraComponents, updateComponents);

        if(mover != null) {
            if (this.mover != null) {
                log.warn("Overwriting existing Mover component");
            }
            this.mover = mover;
        }
    }

    protected void tag(Tag tag){
        this.tags.tag(tag);
    }

}
