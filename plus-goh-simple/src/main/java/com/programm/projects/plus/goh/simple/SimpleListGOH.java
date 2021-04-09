package com.programm.projects.plus.goh.simple;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.IGameContext;
import com.programm.projects.plus.core.IObjectBatch;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SimpleListGOH implements IGameObjectHandler {

    private IEngineContext engineContext;
    private boolean initialized;
    private final List<GameObject> objects = new ArrayList<>();
    private final SimpleListBatch batch = new SimpleListBatch();

    @Override
    public void setup(IEngineContext engineContext) {
        this.engineContext = engineContext;
    }

    @Override
    public void update(IGameContext context) {
        for(int i=0;i<objects.size();i++){
            GameObject object = objects.get(i);

            object.update(context);
            if(object.isDead()){
                objects.remove(i);
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

}
