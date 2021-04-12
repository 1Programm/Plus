package com.programm.projects.plus.core.lifecycle;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObservableLifecycle implements IObservableLifecycle {

    protected final List<Runnable> startupListeners = new ArrayList<>();
    protected final List<Runnable> shutdownListeners = new ArrayList<>();

    protected abstract void onStartup();
    protected abstract void onShutdown();

    protected void onAfterStartup(){ }
    protected void onBeforeShutdown(){ }

    @Override
    public void startup() {
        onStartup();

        for(int i=0;i<startupListeners.size();i++){
            startupListeners.get(i).run();
        }

        onAfterStartup();
    }

    @Override
    //REVERSE ORDER TO STARTUP
    public void shutdown() {
        onBeforeShutdown();

        for(int i=shutdownListeners.size()-1;i>=0;i--){
            shutdownListeners.get(i).run();
        }

        onShutdown();
    }

    @Override
    public void addStartupListener(Runnable onStartup) {
        startupListeners.add(onStartup);
    }

    @Override
    public void addShutdownListener(Runnable onShutdown) {
        shutdownListeners.add(onShutdown);
    }

    @Override
    public void removeStartupListener(Runnable onStartup) {
        startupListeners.remove(onStartup);
    }

    @Override
    public void removeShutdownListener(Runnable onShutdown) {
        shutdownListeners.remove(onShutdown);
    }
}
