package main;

import com.programm.projects.core.GameObject;
import com.programm.projects.core.IComponent;
import com.programm.projects.plus.engine.api.IEngine;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    private static IComponent testComponent = new IComponent() {
        @Override
        public void init() {
            log.info("Test Init");
        }

        @Override
        public void update() {
            log.info("TESSTTT");
        }
    };

    public static void main(String[] args) throws Exception{
        IEngine engine = new SimpleEngine();
        IGameObjectHandler goh = engine.getGOH();

        goh.addObject(GameObject.create()
            .setPosition(100, 100)
            .setSize(32, 32)
            .add(testComponent)
        .build());


        engine.startup();

        Thread.sleep(5000);

        engine.shutdown();
    }

}
