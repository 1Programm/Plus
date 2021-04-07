package com.programm.projects.core;

import com.programm.projects.core.events.IEventHandler;

public interface IEngineContext {

    IEventHandler events();

    IRendererContext rendererContext();

}
