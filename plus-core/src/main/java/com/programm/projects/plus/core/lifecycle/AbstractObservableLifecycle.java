package com.programm.projects.plus.core.lifecycle;

import com.programm.projects.plus.core.exceptions.IThrowableMethod;
import com.programm.projects.plus.core.exceptions.PlusException;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObservableLifecycle implements IObservableLifecycle {

    protected final List<IThrowableMethod> startupListeners = new ArrayList<>();
    protected final List<IThrowableMethod> shutdownListeners = new ArrayList<>();

    protected abstract void onStartup() throws PlusException;
    protected abstract void onShutdown() throws PlusException;

    protected void onAfterStartup(){ }
    protected void onBeforeShutdown(){ }

    @Override
    public void startup() throws PlusException{
        onStartup();

        for(int i=0;i<startupListeners.size();i++){
            startupListeners.get(i).run();
        }

        onAfterStartup();
    }

    @Override
    //REVERSE ORDER TO STARTUP
    public void shutdown() throws PlusException{
        onBeforeShutdown();

        for(int i=shutdownListeners.size()-1;i>=0;i--){
            shutdownListeners.get(i).run();
        }

        onShutdown();
    }

    @Override
    public void addStartupListener(IThrowableMethod onStartup) {
        startupListeners.add(onStartup);
    }

    @Override
    public void addShutdownListener(IThrowableMethod onShutdown) {
        shutdownListeners.add(onShutdown);
    }

    @Override
    public void removeStartupListener(IThrowableMethod onStartup) {
        startupListeners.remove(onStartup);
    }

    @Override
    public void removeShutdownListener(IThrowableMethod onShutdown) {
        shutdownListeners.remove(onShutdown);
    }
}
