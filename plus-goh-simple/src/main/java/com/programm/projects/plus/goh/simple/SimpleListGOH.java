package com.programm.projects.plus.goh.simple;

import com.programm.projects.plus.core.*;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SimpleListGOH implements IGameObjectHandler, IGameContext {

    private IEngineContext engineContext;
    private IRunLoopInfo runLoopInfo;
    private boolean initialized;
    private final List<GameObject> objects = new ArrayList<>();
    private final SimpleListBatch batch = new SimpleListBatch();

    // Game Context
    private GameObject currentObject;

    @Override
    public void setup(IEngineContext engineContext, IRunLoopInfo runLoopInfo) {
        this.engineContext = engineContext;
        this.runLoopInfo = runLoopInfo;
    }

    @Override
    public void update() {
        for(int i=0;i<objects.size();i++){
            currentObject = objects.get(i);

            currentObject.update(this);
            if(currentObject.isDead()){
                objects.remove(i);
                batch.remove(currentObject);
                i--;
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
        batch.add(object);

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
    public GameObject getObject() {
        return currentObject;
    }

    @Override
    public double getDelta() {
        return runLoopInfo.getDelta();
    }
}
