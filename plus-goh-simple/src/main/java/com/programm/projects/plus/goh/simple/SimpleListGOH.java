package com.programm.projects.plus.goh.simple;

import com.programm.projects.plus.core.*;
import com.programm.projects.plus.core.components.Camera;
import com.programm.projects.plus.core.components.Transform;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import com.programm.projects.plus.maths.Vector2f;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class SimpleListGOH implements IGameObjectHandler, IGameContext {

    private static final int UPDATE_TIMER = 60;
    private static final int UPDATE_RANGE = 500 * 500;

    private static final int NEAREST_RANGE = 300 * 300;


    private static float distanceSquared(GameObject o1, GameObject o2, Vector2f velocity){
        float x1 = o1.getTransform().getX();
        float y1 = o1.getTransform().getY();

        if(velocity != null){
            x1 += velocity.getX();
            y1 += velocity.getY();
        }

        float x2 = o2.getTransform().getX();
        float y2 = o2.getTransform().getY();

        float distX = x2 - x1;
        float distY = y2 - y1;

        return (distX * distX + distY * distY);
    }


    private boolean enabled;

    private IEngineContext engineContext;
    private IRunLoopInfo runLoopInfo;
    private boolean initialized;
    private final List<GameObject> objects = new ArrayList<>();

    private int timer = 0;
    private final SimpleListBatch batch = new SimpleListBatch();

    // Game Context
    private GameObject currentObject;
    private final List<GameObject> justEnteredUpdateRangeList = new ArrayList<>();
    private boolean justEnteredUpdateRange;

    @Override
    public void setup(IEngineContext engineContext, IRunLoopInfo runLoopInfo) {
        this.engineContext = engineContext;
        this.runLoopInfo = runLoopInfo;
    }

    @Override
    public boolean setEnabled(boolean enabled) {
        this.enabled = enabled;
        return true;
    }

    @Override
    public void update() {
        if(!enabled) return;

        timer--;
        if(timer <= 0){
            timer = UPDATE_TIMER;
            updateBatch();
        }

        Iterator<GameObject> it = batch.iterator();
        while(it.hasNext()){
            currentObject = it.next();
            justEnteredUpdateRange = false;
            if(justEnteredUpdateRangeList.contains(currentObject)){
                justEnteredUpdateRange = true;
                justEnteredUpdateRangeList.remove(currentObject);
            }

            currentObject.update(this);

            if(currentObject.isDead()){
                objects.remove(currentObject);
                it.remove();
            }
        }
    }

    private void updateBatch(){
        Camera camera = engineContext.scene().getCurrentCamera();
        for(int i=0;i<objects.size();i++){
            GameObject obj = objects.get(i);

            float dist = dist(camera, obj);

            if(batch.contains(obj)){
                if(dist > UPDATE_RANGE){
                    batch.remove(obj);
                }
            }
            else {
                if(dist < UPDATE_RANGE){
                    batch.add(obj);
                    justEnteredUpdateRangeList.add(obj);
                }
            }
        }
    }

    @Override
    public void startup() {
        log.info("[Startup] - Simple List GOH");
        initialized = true;
        for(int i=0;i<objects.size();i++){
            objects.get(i).init(engineContext);
        }
    }

    @Override
    public void shutdown() {
        log.info("[Shutdown] - Simple List GOH");
    }

    @Override
    public void add(GameObject object) {
        objects.add(object);

        //If initialize phase is already over initialize object instantly
        if(initialized){
            object.init(engineContext);
        }
    }

    @Override
    public IObjectBatch getObjectBatch() {
        return batch;
    }

    @Override
    public IObjectBatch getNearestObjectsTo(GameObject object, Vector2f velocity) {
        SimpleListBatch nearestBatch = new SimpleListBatch();

        for(GameObject obj : batch){
            if(obj != object && distanceSquared(object, obj, velocity) <= NEAREST_RANGE){
                nearestBatch.add(obj);
            }
        }

        return nearestBatch;
    }

    @Override
    public IEngineContext engine() {
        return engineContext;
    }

    @Override
    public double getDelta() {
        return runLoopInfo.getDelta();
    }

    @Override
    public GameObject getObject() {
        return currentObject;
    }

    @Override
    public boolean enteredUpdateRange() {
        return justEnteredUpdateRange;
    }

    private float dist(Camera camera, GameObject obj){
        float camX = camera.getX();
        float camY = camera.getY();

        Transform transform = obj.getTransform();
        float objX = transform.getX();
        float objY = transform.getY();

        float distX = camX - objX;
        float distY = camY - objY;

        return distX * distX + distY * distY;
    }
}
