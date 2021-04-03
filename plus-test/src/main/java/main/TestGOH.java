package main;

import com.programm.projects.core.GameObject;
import com.programm.projects.plus.goh.api.IGameObjectHandler;

import java.util.ArrayList;
import java.util.List;

public class TestGOH implements IGameObjectHandler {

    private boolean initialized;
    private final List<GameObject> objects = new ArrayList<>();

    @Override
    public void update() {
        for(int i=0;i<objects.size();i++){
            objects.get(i).update();
        }
    }

    @Override
    public void startup() {
        initialized = true;
        for(int i=0;i<objects.size();i++){
            objects.get(i).init();
        }
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void addObject(GameObject object) {
        objects.add(object);

        if(initialized){ //If initialize phase is already over initialize object instantly
            object.init();
        }
    }
}
