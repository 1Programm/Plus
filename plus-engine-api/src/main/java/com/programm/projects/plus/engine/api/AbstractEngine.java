package com.programm.projects.plus.engine.api;

import com.programm.projects.plus.core.*;
import com.programm.projects.plus.core.events.IEventHandler;
import com.programm.projects.plus.core.lifecycle.AbstractObservableLifecycle;
import com.programm.projects.plus.core.lifecycle.IChainableLifecycle;
import com.programm.projects.plus.core.resource.IResources;
import com.programm.projects.plus.core.settings.EngineSettings;
import com.programm.projects.plus.engine.api.events.EnginePhaseEvent;
import com.programm.projects.plus.engine.api.events.EventStack;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import com.programm.projects.plus.renderer.api.IRenderer;
import com.programm.projects.plus.renderer.api.events.WindowCloseEvent;
import com.programm.projects.plus.resource.api.IResourceManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractEngine extends AbstractObservableLifecycle implements IEngine, IChainableLifecycle, IEngineContext {

    private final EventStack eventStack = new EventStack();
    private final EngineSettings settings = new EngineSettings();

    private IResourceManager resourceManager;
    private IRunLoop runLoop;
    private IRenderer renderer;
    private IGameObjectHandler goh;

    private Scene scene;

    protected EnginePhase phase = EnginePhase.ALIVE;
    private boolean stopRequest = false;



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

        //INIT SETTINGS
        initSettings();

        //ADD SUBSYSTEMS
        addLifecycle(resourceManager);
        addLifecycle(runLoop);
        addLifecycle(renderer);
        addLifecycle(goh);


        //[PREPARE] PHASE
        runLoop.setup(this::update, this);
        renderer.setup(this);
        goh.setup(this, runLoop.info());

        scene.init(goh, renderer, this);

        changePhase(EnginePhase.PREPARED);
    }

    private void initSettings(){
        //Init Window Settings
        String title = resources().getResource("plus.game.window.title").asString("ERR");
        settings().window().setTitle(title);

        int width = resources().getResource("plus.game.window.width").asInt(0);
        settings.window().setWidth(width);

        int height = resources().getResource("plus.game.window.height").asInt(0);
        settings.window().setHeight(height);

        //Init Run loop
        int runLoopSyncFps = resources().getResource("plus.engine.run-loop.fps").asInt(30);
        runLoop.setSync(runLoopSyncFps);
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
        if(stopRequest){ //Stop on request and init shutdown phase
            shutdown();
            return;
        }

        //Handle events
        eventStack.pollEvents();

        //Update all objects
        goh.update();

        //Get batch of objects which will be rendered
        IObjectBatch objectBatch = goh.getObjectBatch();

        //Render scene
        renderer.setRenderableBatch(objectBatch);
        renderer.update();
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
    public EngineSettings settings() {
        return settings;
    }

    @Override
    public ISceneContext scene() {
        return scene;
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
