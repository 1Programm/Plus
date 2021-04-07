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
    public void setup(Runnable updateCallback) {
        this.updateCallback = updateCallback;
    }

    @Override
    public void startup() {
        log.info("[Startup] - Test Run Loop");
        if(running){
            log.warn("Loop is already running!");
            return;
        }

        running = true;
        thread.start();
    }

    @Override
    public void shutdown() {
        log.info("[Shutdown] - Test Run Loop");
        running = false;
        thread.interrupt();
    }

    private void run() {
        int millis = 1000 / fps;

        try {
            while (running) {
                Thread.sleep(millis);
                updateCallback.run();
            }
        }catch (InterruptedException ignored){}
    }
}
