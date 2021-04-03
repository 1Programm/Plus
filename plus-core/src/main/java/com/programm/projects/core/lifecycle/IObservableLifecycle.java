package com.programm.projects.core.lifecycle;

public interface IObservableLifecycle extends ILifecycle{

    void addStartupListener(Runnable onStartup);
    void addShutdownListener(Runnable onShutdown);

}
