package com.programm.plus.goh.api;

import com.programm.plus.camera.api.Camera;
import com.programm.plus.collision.api.CollisionTester;
import com.programm.plus.core.GameContext;
import com.programm.plus.core.obj.GameObjectBuilder;
import com.programm.plus.core.obj.ObjectInstance;
import com.programm.plus.tags.Tag;
import com.programm.plus.tags.TagAction;
import com.programm.plus.tags.TagSystem;

public interface GameObjectHandler {

    void init(GameContext context, TagSystem tagSystem, Camera camera);

    GameObjectBuilder createEmpty();

    GameObjectBuilder create(ObjectInstance instance);

    void update(CollisionTester collisionTester);

    void runTagAction(Tag tag, TagAction action);


}
