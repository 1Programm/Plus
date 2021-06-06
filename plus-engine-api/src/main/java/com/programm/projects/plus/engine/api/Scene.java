package com.programm.projects.plus.engine.api;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.IObjectBatch;
import com.programm.projects.plus.core.ISceneContext;
import com.programm.projects.plus.core.components.Camera;
import com.programm.projects.plus.core.events.RegisterCameraEvent;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import com.programm.projects.plus.goh.api.IObjectConsumer;
import com.programm.projects.plus.maths.Vector2f;
import com.programm.projects.plus.renderer.api.IRenderer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Scene implements ISceneContext {

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
        initScene(this::addObject);

        if(camera == null){
            log.error("No Camera found in scene!");
        }
    }

    protected abstract void initScene(IObjectConsumer objects);

    @Override
    public void addObject(GameObject object) {
        goh.add(object);
    }

    @Override
    public IObjectBatch getNearestObjectsTo(GameObject object, Vector2f velocity) {
        return goh.getNearestObjectsTo(object, velocity);
    }

    @Override
    public Camera getCurrentCamera() {
        return camera;
    }


}
