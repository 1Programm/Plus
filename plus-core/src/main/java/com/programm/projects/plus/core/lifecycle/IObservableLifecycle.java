package com.programm.projects.plus.core.lifecycle;

public interface IObservableLifecycle extends ILifecycle{

    void addStartupListener(Runnable onStartup);
    void addShutdownListener(Runnable onShutdown);

    void removeStartupListener(Runnable onStartup);
    void removeShutdownListener(Runnable onShutdown);

}
