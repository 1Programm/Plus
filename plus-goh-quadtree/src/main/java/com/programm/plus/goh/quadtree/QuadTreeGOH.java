package com.programm.plus.goh.quadtree;

import com.programm.plus.collision.api.CollisionTester;
import com.programm.plus.core.GameContext;
import com.programm.plus.core.obj.GameObjectBuilder;
import com.programm.plus.core.obj.ObjectInstance;
import com.programm.plus.goh.api.GameObjectHandler;
import com.programm.plus.tags.Tag;
import com.programm.plus.tags.TagAction;
import com.programm.plus.tags.TagSystem;

public class QuadTreeGOH implements GameObjectHandler {

    @Override
    public void init(GameContext context, TagSystem tagSystem) {

    }

    @Override
    public GameObjectBuilder createEmpty() {
        return null;
    }

    @Override
    public GameObjectBuilder create(ObjectInstance objectInstance) {
        return null;
    }

    @Override
    public void update(CollisionTester collisionTester) {

    }

    @Override
    public void runTagAction(Tag tag, TagAction tagAction) {

    }
}
