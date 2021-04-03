package main;

import com.programm.projects.plus.engine.api.AbstractEngine;
import com.programm.projects.plus.engine.api.IRunLoop;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleEngine extends AbstractEngine {

    private IRunLoop runLoop;

    public SimpleEngine() {
        setRunLoop(new TestRunLoop(1));
    }

    @Override
    protected void onStartup() {
        log.info("Starting Engine");
    }

    @Override
    protected void onShutdown() {
        log.info("Stopping Engine");
    }

    @Override
    protected void update() {
        log.error("Test");
    }
}
