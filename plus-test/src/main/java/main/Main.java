package main;

import com.programm.projects.core.events.IEvent;
import com.programm.projects.plus.engine.api.IEngine;
import com.programm.projects.plus.engine.api.events.EngineEvent;
import com.programm.projects.plus.engine.api.events.EngineShutdownEvent;
import com.programm.projects.plus.engine.api.events.EngineStartupEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    private static void onStart(EngineStartupEvent e){
        log.info("ON START");
    }

    private static void onStop(EngineShutdownEvent e){
        log.info("ON STOP");
    }

    private static void onEngine(EngineEvent e){
        log.info("{}", e);
    }

    public static void main(String[] args) throws Exception{
        IEngine engine = new SimpleEngine();
        engine.events().listenFor(EngineStartupEvent.class, Main::onStart);
        engine.events().listenFor(EngineShutdownEvent.class, Main::onStop);
        engine.events().listenFor(EngineEvent.class, Main::onEngine);
        engine.startup();
    }

}
