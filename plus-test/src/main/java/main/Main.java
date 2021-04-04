package main;

import com.programm.projects.core.GameObject;
import com.programm.projects.plus.engine.api.IEngine;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import com.programm.projects.plus.renderer.api.events.WindowCloseEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    private static void onWindowClose(WindowCloseEvent e){
        log.info("Window Close!!!!!!!");
    }

    public static void main(String[] args) throws Exception{
        IEngine engine = new SimpleEngine();
        engine.events().listenFor(WindowCloseEvent.class, Main::onWindowClose);
        IGameObjectHandler goh = engine.getGOH();

        goh.addObject(GameObject.create()
            .setPosition(100, 100)
            .setSize(32, 32)
        .build());


        engine.startup();

//        Thread.sleep(5000);
//
//        engine.shutdown();
    }

}
