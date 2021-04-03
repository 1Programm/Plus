package main;

import com.programm.projects.plus.engine.api.AbstractEngine;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleEngine extends AbstractEngine {

    public SimpleEngine() {
        setRunLoop(new TestRunLoop(1));
        setGOH(new TestGOH());
    }

    @Override
    protected void onStartup() {
        log.info("Starting Engine");
    }

    @Override
    protected void onShutdown() {
        log.info("Stopping Engine");
    }

}
