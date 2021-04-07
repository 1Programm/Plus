package com.programm.projects.plus.engine.api;

import com.programm.projects.core.IEngineContext;
import com.programm.projects.core.IObjectBatch;
import com.programm.projects.core.IRendererContext;
import com.programm.projects.core.events.EventBus;
import com.programm.projects.core.events.IEventHandler;
import com.programm.projects.core.lifecycle.AbstractObservableLifecycle;
import com.programm.projects.core.lifecycle.IChainableLifecycle;
import com.programm.projects.plus.engine.api.events.EngineShutdownEvent;
import com.programm.projects.plus.engine.api.events.EngineStartupEvent;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import com.programm.projects.plus.renderer.api.IRenderer;
import com.programm.projects.plus.renderer.api.WindowInfo;
import com.programm.projects.plus.renderer.api.events.WindowCloseEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractEngine extends AbstractObservableLifecycle implements IEngine, IChainableLifecycle, IEngineContext {

    private final GameStackContext gameContext = new GameStackContext();
    private final EventBus eventBus = new EventBus();

    protected IRunLoop runLoop;
    protected IGameObjectHandler goh;
    protected IRenderer renderer;

    protected EnginePhase phase = EnginePhase.ALIVE;
    private boolean stopRequest = false;

    //TODO EngineInfo -> WindowInfo with defaults

    //TEMPORARY:
    private final WindowInfo windowInfo = new WindowInfo("Plus Engine", 600, 500);


    /*
     * -- LIFECYCLE METHODS --
     */
    @Override
    protected void onStartup() {
        phase = EnginePhase.STARTING;
        log.info("[Startup] - Engine");

        events().listenFor(WindowCloseEvent.class, this::onWindowClose);

        //[PREPARE] PHASE
        runLoop.setup(this::update);
        renderer.setup(events(), windowInfo);
        goh.setup(this);


        phase = EnginePhase.PREPARED;
    }

    @Override
    protected void onAfterStartup() {
        phase = EnginePhase.STARTED;
        events().dispatch(new EngineStartupEvent(this));
    }

    @Override
    protected void onBeforeShutdown() {
        phase = EnginePhase.CLEANUP;
        events().dispatch(new EngineShutdownEvent(this));
    }

    @Override
    protected void onShutdown() {
        log.info("[Shutdown] - Engine");
        phase = EnginePhase.SHUTDOWN;
    }

    //------------------

    private void onWindowClose(WindowCloseEvent e){
        stopRequest = true;
    }

    protected void update(){
        if(stopRequest){
            shutdown();
            return;
        }

        //Update all objects
        goh.update(gameContext);

        //Get batch of objects which will be rendered
        IObjectBatch objectBatch = goh.getObjectBatch();

        //Render scene
        renderer.setRenderableBatch(objectBatch);
        renderer.update(gameContext);
    }




    /*
     * GETTER AND SETTER
     */

    @Override
    public void setRunLoop(IRunLoop runLoop) {
        if(this.runLoop != null){
            removeLifecycle(this.runLoop);
        }

        this.runLoop = runLoop;
        addLifecycle(runLoop);
    }

    @Override
    public void setGOH(IGameObjectHandler goh) {
        if(this.goh != null){
            removeLifecycle(this.goh);
        }

        this.goh = goh;
        addLifecycle(goh);
    }

    @Override
    public void setRenderer(IRenderer renderer) {
        if(this.renderer != null){
            removeLifecycle(this.renderer);
        }

        this.renderer = renderer;
        addLifecycle(renderer);
    }

    @Override
    public IEventHandler events() {
        return eventBus;
    }

    @Override
    public IRendererContext rendererContext() {
        return renderer;
    }

    @Override
    public EnginePhase phase() {
        return phase;
    }

    @Override
    public IGameObjectHandler getGOH() {
        return goh;
    }

}
