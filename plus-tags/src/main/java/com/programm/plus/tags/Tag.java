package com.programm.plus.tags;

import com.programm.plus.collision.api.Collider;
import com.programm.plus.core.component.Component;
import com.programm.plus.core.component.Mover;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Tag {

    public static final Tag GAME_OBJECT = new Tag("GameObject");

    public static final Tag OBJECT_STATIC = new Tag("StaticObject", GAME_OBJECT);       // Objects which will not move

    public static final Tag OBJECT_IMMOVABLE = new Tag("ImmovableObject", GAME_OBJECT); // Objects which can not be moved

    public static final Tag OBJECT_ENTITY = new Tag("EntityObject", GAME_OBJECT)        // Objects which can move
            .addRequiredComponent(Mover.class);

    public static final Tag ENTITY_MOVABLE = new Tag("MovableObject", OBJECT_ENTITY)    // Entities which are meant to be moved through collision
            .addRequiredComponent(Collider.class);

    public static final Tag ENTITY_NO_COLLISION_RESOLVING = new Tag("NoCollisionResolving", ENTITY_MOVABLE);

    public static final Tag UI_Element = new Tag("UiElement");




    @Getter private String name;
    Tag parent;
    List<RequiredClassInfo> requiredComponents;

    public Tag(String name){
        init(name, null);
    }

    public Tag(String name, Tag parent){
        init(name, parent);
    }

    private void init(String name, Tag parent){
        this.name = name;
        this.parent = parent;
        this.requiredComponents = new ArrayList<>();
    }

    public Tag addRequiredComponent(Class<? extends Component> requiredComponent, Object... defaultConstructorParams){
        this.requiredComponents.add(new RequiredClassInfo(requiredComponent, defaultConstructorParams));

        return this;
    }

    public boolean is(Tag tag){
        if(equals(tag)) return true;
        if(parent == null) return false;
        return parent.is(tag);
    }

    public boolean equals(Tag tag) {
        return this.name.equals(tag.name);
    }

    public boolean is(String tag){
        if(equals(tag)) return true;
        if(parent == null) return false;
        return parent.is(tag);
    }

    public boolean equals(String tag) {
        return this.name.equals(tag);
    }
}
