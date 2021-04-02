package com.programm.plus.engine.api;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public abstract class GameLoop {

    private boolean running;
    private Supplier<Boolean> window_stopOn;
    private Supplier<Boolean> extra_stopOn;
    private boolean onNewThread;

    private Runnable initRun;
    protected Runnable updateRun;
    private Runnable afterRun;

    public final void init(Runnable initRun, Runnable updateRun, Runnable afterRun, Supplier<Boolean> window_stopOn){
        this.initRun = initRun;
        this.updateRun = updateRun;
        this.afterRun = afterRun;
        this.window_stopOn = window_stopOn;
    }

    public final void start(){
        if(running) return;

        log.info("Starting Loop{} on a new Thread.", onNewThread ? "" : " not");

        running = true;

        if(onNewThread) {
            Thread thread = new Thread(this::_run);
            thread.start();
        }
        else {
            _run();
        }
    }

    public final void stop(){
        if(!running) return;

        running = false;
    }

    public GameLoop stopOn(Supplier<Boolean> stopOn){
        this.extra_stopOn = stopOn;
        return this;
    }

    private void _run(){
        initRun.run();
        run();
        afterRun.run();
    }

    protected abstract void run();
    protected abstract float getDeltaTime();

    protected boolean loopCondition(){
        if(extra_stopOn != null){
            return running && !window_stopOn.get() && !extra_stopOn.get();
        }

        return running && !window_stopOn.get();
    }

    public GameLoop newThread(){
        this.onNewThread = true;
        return this;
    }

}
