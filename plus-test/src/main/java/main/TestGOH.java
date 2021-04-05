package main;

import com.programm.projects.core.IGameContext;
import com.programm.projects.core.GameObject;
import com.programm.projects.core.IObjectBatch;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TestGOH implements IGameObjectHandler {

    private boolean initialized;
    private final List<GameObject> objects = new ArrayList<>();
    private final TestBatch batch = new TestBatch();

    @Override
    public void update(IGameContext context) {
        for(int i=0;i<objects.size();i++){
            objects.get(i).update(context);
        }
    }

    @Override
    public void startup() {
        log.info("[Startup] - Test GOH");
        initialized = true;
        for(int i=0;i<objects.size();i++){
            objects.get(i).init();
        }
    }

    @Override
    public void shutdown() {
        log.info("[Shutdown] - Test GOH");
    }

    @Override
    public void addObject(GameObject object) {
        objects.add(object);
        batch.add(object);

        if(initialized){ //If initialize phase is already over initialize object instantly
            object.init();
        }
    }

    @Override
    public IObjectBatch getObjectBatch() {
        return batch;
    }
}
