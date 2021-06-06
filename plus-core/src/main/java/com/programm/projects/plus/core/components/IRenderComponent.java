package com.programm.projects.plus.core.components;

import com.programm.projects.plus.core.IComponent;
import com.programm.projects.plus.core.IEngineContext;

public interface IRenderComponent extends IComponent {

    @Override
    default void init(IEngineContext context) {
        context.renderer().init(this);
    }

}
