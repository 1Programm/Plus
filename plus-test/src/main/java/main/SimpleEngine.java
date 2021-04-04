package main;

import com.programm.projects.plus.engine.api.AbstractEngine;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleEngine extends AbstractEngine {

    public SimpleEngine() {
        setRunLoop(new TestRunLoop(1));
        setGOH(new TestGOH());
        setRenderer(new TestRenderer());
    }

    @Override
    protected void onStartup() {
        log.info("[Startup] - Engine");
    }

    @Override
    protected void onShutdown() {
        log.info("[Shutdown] - Engine");
    }
}
