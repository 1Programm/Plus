package com.programm.projects.plus.core.lifecycle;

import com.programm.projects.plus.core.exceptions.IThrowableMethod;

public interface IObservableLifecycle extends ILifecycle{

    void addStartupListener(IThrowableMethod onStartup);
    void addShutdownListener(IThrowableMethod onShutdown);

    void removeStartupListener(IThrowableMethod onStartup);
    void removeShutdownListener(IThrowableMethod onShutdown);

}
