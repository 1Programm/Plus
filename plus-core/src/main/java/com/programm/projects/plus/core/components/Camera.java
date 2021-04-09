package com.programm.projects.plus.core.components;

import com.programm.projects.plus.core.AbstractComponent;
import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.events.RegisterCameraEvent;

public class Camera extends AbstractComponent {

    @Override
    public void init(IEngineContext context) {
        context.events().dispatch(new RegisterCameraEvent(this));
    }

    public float getX(){
        return transform().getX();
    }

    public float getY(){
        return transform().getY();
    }

}
