package main;

import com.programm.projects.plus.engine.api.IRunLoop;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestRunLoop implements IRunLoop {

    private final int fps;
    private Runnable updateCallback;
    private boolean running;
    private final Thread thread = new Thread(this::run, "Test Loop");

    public TestRunLoop(int fps) {
        this.fps = fps;
    }

    @Override
    public void startup() {
        if(running){
            log.warn("Loop is already running!");
            return;
        }

        running = true;
        thread.start();
    }

    @Override
    public void shutdown() {
        running = false;
        thread.interrupt();
    }

    private void run() {
        log.info("Starting Test Run Loop...");

        int millis = 1000 / fps;

        try {
            while (running) {
                Thread.sleep(millis);
                updateCallback.run();
            }
        }catch (InterruptedException ignored){}
    }

    @Override
    public void init(Runnable updateCallback) {
        this.updateCallback = updateCallback;
    }
}