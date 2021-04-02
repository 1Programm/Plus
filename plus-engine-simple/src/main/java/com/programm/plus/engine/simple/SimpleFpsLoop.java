package com.programm.plus.engine.simple;

import com.programm.plus.engine.api.GameLoop;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleFpsLoop extends GameLoop {

    private double fps;
    private float _fps;
    @Setter private boolean printFps;

    public SimpleFpsLoop(double fps){
        init(fps, false);
    }

    public SimpleFpsLoop(double fps, boolean printFps){
        init(fps, printFps);
    }

    private void init(double fps, boolean printFps){
        this.fps = fps;
        this._fps = (float)(1.0/fps);
        this.printFps = printFps;
    }

    @Override
    protected void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = fps;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(loopCondition()){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1){
                updateRun.run();
                if(printFps) updates++;
                delta--;
            }



            if(printFps) {
                frames++;

                if(System.currentTimeMillis() - timer > 1000){
                    timer += 1000;
                    log.trace("FPS: {} - TICKS: {}", frames, updates);
                    frames = 0;
                    updates = 0;
                }
            }
        }
    }

    @Override
    protected float getDeltaTime() {
        return _fps;
    }
}
