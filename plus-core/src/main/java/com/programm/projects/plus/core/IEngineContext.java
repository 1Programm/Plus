package com.programm.projects.plus.core;

import com.programm.projects.plus.core.events.IEventHandler;
import com.programm.projects.plus.core.resource.IResources;
import com.programm.projects.plus.core.settings.EngineSettings;

public interface IEngineContext {

    IEventHandler events();

    IResources resources();

    EngineSettings settings();

    IRenderContext rendererContext();

}
