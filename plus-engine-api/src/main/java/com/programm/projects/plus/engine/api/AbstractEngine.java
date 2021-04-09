package com.programm.projects.plus.engine.api;

import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.IObjectBatch;
import com.programm.projects.plus.core.IRenderContext;
import com.programm.projects.plus.core.events.IEventHandler;
import com.programm.projects.plus.core.lifecycle.AbstractObservableLifecycle;
import com.programm.projects.plus.core.lifecycle.IChainableLifecycle;
import com.programm.projects.plus.core.resource.IResources;
import com.programm.projects.plus.engine.api.events.EnginePhaseEvent;
import com.programm.projects.plus.engine.api.events.EventStack;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import com.programm.projects.plus.renderer.api.IRenderer;
import com.programm.projects.plus.renderer.api.WindowInfo;
import com.programm.projects.plus.renderer.api.events.WindowCloseEvent;
import com.programm.projects.plus.resource.api.IResourceManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractEngine extends AbstractObservableLifecycle implements IEngine, IChainableLifecycle, IEngineContext {

    private final GameStackContext gameContext = new GameStackContext();
    private final EventStack eventStack = new EventStack();

    protected IResourceManager resourceManager;
    protected IRunLoop runLoop;
    protected IRenderer renderer;
    protected IGameObjectHandler goh;

    private Scene scene;

    protected EnginePhase phase = EnginePhase.ALIVE;
    private boolean stopRequest = false;

    //TODO EngineInfo -> WindowInfo with defaults

    //TEMPORARY:
    private final WindowInfo windowInfo = new WindowInfo("Plus Engine", 600, 500);

    protected abstract IResourceManager initResourceManager();
    protected abstract IRunLoop initRunLoop();
    protected abstract IRenderer initRenderer();
    protected abstract IGameObjectHandler initGOH();

    /*
     * -- LIFECYCLE METHODS --
     */
    @Override
    protected void onStartup() {
        log.info("[Startup] - Engine");
        changePhase(EnginePhase.STARTING);

        events().listenFor(WindowCloseEvent.class, this::onWindowClose);

        //INIT SYSTEMS
        this.resourceManager = initResourceManager();
        this.runLoop = initRunLoop();
        this.goh = initGOH();
        this.renderer = initRenderer();


        //LOAD RESOURCES
        resourceManager.loadStaticResources();

        //ADD SUBSYSTEMS
        addLifecycle(resourceManager);
        addLifecycle(runLoop);
        addLifecycle(renderer);
        addLifecycle(goh);


        //[PREPARE] PHASE
        runLoop.setup(this::update, this);
        renderer.setup(events(), windowInfo);
        goh.setup(this);

        scene.init(goh, renderer, this);

        changePhase(EnginePhase.PREPARED);
    }

    @Override
    protected void onAfterStartup() {
        scene.load();

        changePhase(EnginePhase.STARTED);
        EngineStaticLogger.logStartup(resourceManager);
    }

    @Override
    protected void onBeforeShutdown() {
        changePhase(EnginePhase.CLEANUP);
    }

    @Override
    protected void onShutdown() {
        log.info("[Shutdown] - Engine");
        changePhase(EnginePhase.SHUTDOWN);
        EngineStaticLogger.logShutdown(resourceManager);
    }

    //------------------

    private void changePhase(EnginePhase phase){
        EnginePhase before = this.phase;
        this.phase = phase;
        events().dispatch(new EnginePhaseEvent(this, before));
    }

    private void onWindowClose(WindowCloseEvent e){
        stopRequest = true;
    }

    protected void update(){
        if(stopRequest){
            shutdown();
            return;
        }

        eventStack.pollEvents();

        //Update all objects
        goh.update(gameContext);

        //Get batch of objects which will be rendered
        IObjectBatch objectBatch = goh.getObjectBatch();

        //Render scene
        renderer.setRenderableBatch(objectBatch);
        renderer.update(gameContext);
    }


    // OTHER METHODS

    @Override
    public IEventHandler events() {
        return eventStack;
    }

    @Override
    public IResources resources() {
        return resourceManager;
    }

    @Override
    public IRenderContext rendererContext() {
        return renderer;
    }

    @Override
    public EnginePhase phase() {
        return phase;
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
