package com.programm.projects.plus.engine.api;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.components.Camera;
import com.programm.projects.plus.core.events.RegisterCameraEvent;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import com.programm.projects.plus.goh.api.IObjectConsumer;
import com.programm.projects.plus.renderer.api.IRenderer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Scene {

    private IGameObjectHandler goh;
    private IRenderer renderer;
    private Camera camera;

    public final void init(IGameObjectHandler goh, IRenderer renderer, IEngineContext context){
        this.goh = goh;
        this.renderer = renderer;

        context.events().listenFor(RegisterCameraEvent.class, this::onRegisterCamera);
    }

    private void onRegisterCamera(RegisterCameraEvent event){
        if(camera != null){
            log.error("Camera already registered!");
            return;
        }

        camera = event.getCamera();
        renderer.addCamera(camera);
    }

    public final void load(){
        initScene(this::initAddObject);

        if(camera == null){
            log.error("No Camera found in scene!");
        }
    }

    private void initAddObject(GameObject gameObject){
        goh.add(gameObject);
    }

    protected abstract void initScene(IObjectConsumer objects);


}
