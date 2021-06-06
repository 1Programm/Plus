package com.programm.projects.plus.engine.simple;

import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.engine.api.EnginePhase;
import com.programm.projects.plus.engine.api.IRunLoop;
import com.programm.projects.plus.core.IRunLoopInfo;
import com.programm.projects.plus.engine.api.events.EnginePhaseEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleRunLoop implements IRunLoop, IRunLoopInfo {

    private int fps;
    private Runnable updateCallback;
    private boolean running;
    private final Thread thread = new Thread(this::run, "Simple Loop");

    //Saved deltas
    private double testd;
    private int testi;


    //current info
    private int frames;
    private int updates;
    private double delta;

    @Override
    public boolean setEnabled(boolean enabled) {
        return false;
    }

    @Override
    public void setup(Runnable updateCallback, IEngineContext context) {
        this.updateCallback = updateCallback;

        context.events().listenFor(EnginePhaseEvent.class, this::onEnginePhaseChanged);
    }

    @Override
    public void setSync(int fps) {
        this.fps = fps;
    }

    private void onEnginePhaseChanged(EnginePhaseEvent event){
        if(event.getPhase() == EnginePhase.STARTED){
            log.info("[Startup] - Simple Run Loop Thread");
            thread.start();
        }
    }

    @Override
    public void startup() {
        log.info("[Startup] - Simple Run Loop");
        if(running){
            log.warn("Loop is already running!");
            return;
        }

        running = true;
    }

    @Override
    public void shutdown() {
        log.info("[Shutdown] - Simple Run Loop");
        running = false;
        thread.interrupt();
    }

    private void run() {
        long lastTime = System.nanoTime();
        double ns = 1000000000.0;
        double ms = 1.0 / ns;
        double fpsNs = ns / fps;
        long timer = System.currentTimeMillis();
        float delta = 0;

        while(running){
            long now = System.nanoTime();
            long diff = (now - lastTime);

            delta += diff;
            calcDelta(delta * ms);
            lastTime = now;

            while(delta >= fpsNs){
                updateCallback.run();
                updates++;
                delta -= fpsNs;
            }

            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                frames = 0;
                updates = 0;
            }
        }
    }

    private void calcDelta(double curDelta){
        testd += curDelta;
        testi ++;

        if(testi >= 1000){
            testd /= 10;
            testi /= 10;
        }

        this.delta = testd / testi;

//        if(this.delta > 10){
//            System.err.println("Delta: " + delta + ", d: " + testd + ", testi: " + testi + ", curDelta: " + curDelta);
//        }
    }

    @Override
    public IRunLoopInfo info() {
        return this;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getFps() {
        return frames;
    }

    @Override
    public int getTicks() {
        return updates;
    }

    @Override
    public double getDelta() {
        return delta;
    }
}
