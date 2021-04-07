package com.programm.projects.plus.engine.simple;

import com.programm.projects.plus.engine.api.IRunLoop;
import com.programm.projects.plus.engine.api.IRunLoopInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleRunLoop implements IRunLoop, IRunLoopInfo {

    private final int fps;
    private Runnable updateCallback;
    private boolean running;
    private final Thread thread = new Thread(this::run, "Simple Loop");

    //current info
    private int frames;
    private int updates;

    public SimpleRunLoop(int fps) {
        this.fps = fps;
    }

    @Override
    public void setup(Runnable updateCallback) {
        this.updateCallback = updateCallback;
    }

    @Override
    public void startup() {
        log.info("[Startup] - Simple Run Loop");
        if(running){
            log.warn("Loop is already running!");
            return;
        }

        running = true;
        thread.start();
    }

    @Override
    public void shutdown() {
        log.info("[Shutdown] - Simple Run Loop");
        running = false;
        thread.interrupt();
    }

    private void run() {
        long lastTime = System.nanoTime();
        double ns = 1000000000.0 / fps;
        double delta = 0;
        long timer = System.currentTimeMillis();

        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1){
                updateCallback.run();
                this.updates++;
                delta--;
            }

            this.frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                this.frames = 0;
                this.updates = 0;
            }

//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException ignored) { }
        }
    }

    @Override
    public IRunLoopInfo info() {
        return this;
    }

    @Override
    public int getFps() {
        return frames;
    }

    @Override
    public int getTicks() {
        return updates;
    }
}
