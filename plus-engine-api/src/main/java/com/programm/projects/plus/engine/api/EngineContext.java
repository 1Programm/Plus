package com.programm.projects.plus.engine.api;

import com.programm.projects.core.GameObject;
import com.programm.projects.core.IGameContext;

public class EngineContext implements IGameContext {

    private static final int MAX_INDEX = 10;

    private final GameObject[] stack;
    private int index;

    public EngineContext(){
        this.stack = new GameObject[MAX_INDEX];
        this.index = -1;
    }

    private EngineContext(GameObject[] stack, int index){
        this.stack = stack;
        this.index = index;
    }

    @Override
    public GameObject getObject() {
        if(index == -1) return null;
        return stack[index];
    }

    @Override
    public IGameContext getParentContext() {
        if(index == -1) return null;

        return new EngineContext(stack, index - 1);
    }

    @Override
    public IGameContext createFromNewParent(GameObject object) {
        index++;
        stack[index] = object;

        return this;
    }

    @Override
    public void revert() {
        index--;
    }

}
