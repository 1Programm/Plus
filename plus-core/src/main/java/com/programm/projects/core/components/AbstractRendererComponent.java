package com.programm.projects.core.components;

import com.programm.projects.core.IEngineContext;

public abstract class AbstractRendererComponent implements IRenderComponent{

    private boolean initialized;

    protected abstract void onInit(IEngineContext context);

    @Override
    public final void init(IEngineContext context) {
        this.initialized = initialized;
        onInit(context);
    }

    @Override
    public final boolean isInitialized() {
        return initialized;
    }
}
