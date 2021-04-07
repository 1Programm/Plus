package com.programm.projects.plus.core;

import com.programm.projects.plus.core.events.IEventHandler;

public interface IEngineContext {

    IEventHandler events();

    IRenderContext rendererContext();

}
