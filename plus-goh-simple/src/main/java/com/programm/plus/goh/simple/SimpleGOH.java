package com.programm.plus.goh.simple;

import com.programm.plus.camera.api.Camera;
import com.programm.plus.collision.api.Collider;
import com.programm.plus.collision.api.CollisionTester;
import com.programm.plus.core.GameContext;
import com.programm.plus.core.component.*;
import com.programm.plus.core.obj.GameObject;
import com.programm.plus.core.obj.GameObjectBuilder;
import com.programm.plus.core.obj.ObjectInstance;
import com.programm.plus.tags.Tag;
import com.programm.plus.tags.TagAction;
import com.programm.plus.goh.api.GameObjectHandler;
import com.programm.plus.tags.TagSystem;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class SimpleGOH implements GameObjectHandler {

    private List<GameObject> objects = new ArrayList<>();
    private GameContext context;
    private TagSystem tagSystem;
    private Camera camera;

    @Override
    public void init(GameContext context, TagSystem tagSystem, Camera camera) {
        this.context = context;
        this.tagSystem = tagSystem;
        this.camera = camera;
    }

    @Override
    public GameObjectBuilder createEmpty() {
        return new GameObjectBuilder(this::addObject, tagSystem.getObjectInitializer());
    }

    @Override
    public GameObjectBuilder create(ObjectInstance instance) {
        return new GameObjectBuilder(this::addObject, tagSystem.getObjectInitializer(), instance);
    }

    private void addObject(GameObject obj){
        if(!context.getEngineSettings().isRotationEnabled()){
            if(obj.getTransform().getRotation() != 0) {
                log.warn("Object has rotation but rotation was not enabled!");
            }
        }

        this.objects.add(obj);
        obj.getComponents().onCreate();
    }

    @Override
    public void update(CollisionTester collisionTester) {
        camera.onUpdate(context);

        for (int i=0;i<objects.size();i++){
            GameObject obj = objects.get(i);

            Map<Class<? extends UpdateComponent>, UpdateComponent> uComps = obj.getComponents().getUpdateComponents();
            for(Class<? extends UpdateComponent> cls : uComps.keySet()){
                UpdateComponent cmp = uComps.get(cls);
                cmp.onUpdate(context);
            }

            obj.getComponents().clearUpdateComponents();

            Mover mover = obj.getComponents().getMover();
            if(mover != null){
                if(!obj.hasTag(Tag.OBJECT_IMMOVABLE)) {
                    mover.onUpdate(context);
                }
            }

            if(collisionTester != null) {
                Collider collider = obj.getComponents().get(Collider.class);

                if (collider != null) {
                    collisionTest(obj, collider, collisionTester);
                }
            }
        }

        for(int i=0;i<objects.size();i++){
            if(!objects.get(i).isAlive()){
                objects.get(i).getComponents().onDestroy();
                objects.remove(i);
                i--;
            }
        }
    }

    @Override
    public void runTagAction(Tag tag, TagAction tagAction) {
        for(GameObject obj : objects){
            if(obj.hasTag(tag)){
                tagAction.act(context, obj);
            }
        }
    }

    private void collisionTest(GameObject obj, Collider collider, CollisionTester collisionTester){
        for(GameObject o2 : objects){
            if(obj != o2){
                if(o2.getComponents().get(Collider.class) != null) {
                    collisionTester.test(obj, o2, collider);
                }
            }
        }
    }
}
