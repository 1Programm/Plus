package com.programm.plus.core.obj;

import com.programm.plus.core.component.*;
import com.programm.plus.maths.Transform2f;
import com.programm.plus.tags.Tag;
import com.programm.plus.tags.TagHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class GameObject {

    @Getter private Transform2f transform;
    @Getter private ComponentHandler components;
    private TagHandler tags;
    @Getter private boolean alive;

    GameObject(Transform2f transform, ComponentHandler components, TagHandler tags) {
        this.transform = transform;
        this.components = components;
        this.tags = tags;
        this.alive = true;
    }

    public boolean hasTag(Tag tag){
        return tags.hasTag(tag);
    }

    public void die(){
        this.alive = false;
    }

}
