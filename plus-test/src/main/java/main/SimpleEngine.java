package main;

import com.programm.projects.plus.engine.api.AbstractEngine;
import com.programm.projects.plus.renderer.swing.SwingRenderer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleEngine extends AbstractEngine {

    public SimpleEngine() {
        setRunLoop(new TestRunLoop(30));
        setRenderer(new SwingRenderer());
        setGOH(new TestGOH());
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
