package com.programm.projects.core.lifecycle;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObservableLifecycle implements IObservableLifecycle {

    protected final List<Runnable> startupListeners = new ArrayList<>();
    protected final List<Runnable> shutdownListeners = new ArrayList<>();

    protected abstract void onStartup();
    protected abstract void onShutdown();

    @Override
    public final void startup() {
        onStartup();

        startupListeners.forEach(Runnable::run);
    }

    @Override
    public final void shutdown() {
        onShutdown();

        shutdownListeners.forEach(Runnable::run);
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
