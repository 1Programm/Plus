package com.programm.projects.plus.core;

import com.programm.projects.plus.core.events.IEventHandler;
import com.programm.projects.plus.core.resource.IResources;

public interface IEngineContext {

    IEventHandler events();

    IResources resources();

    IRenderContext rendererContext();

}
