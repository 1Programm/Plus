package com.programm.plus.engine.simple;

import com.programm.plus.engine.api.GameLoop;

public class SimpleLoop extends GameLoop {

    private float delta;

    @Override
    protected void run() {
        long first = System.currentTimeMillis();

        while (loopCondition()){
            long second = System.currentTimeMillis();

            delta = (float)((second - first) / 1000.0);
            first = second;

            updateRun.run();
        }
    }

    @Override
    protected float getDeltaTime() {
        return delta;
    }
}
