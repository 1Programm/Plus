package com.programm.projects.plus.engine.api;

import com.programm.projects.plus.collision.api.ICollisionHandler;
import com.programm.projects.plus.core.*;
import com.programm.projects.plus.core.events.IEventHandler;
import com.programm.projects.plus.core.exceptions.IThrowableMethod;
import com.programm.projects.plus.core.exceptions.PlusException;
import com.programm.projects.plus.core.exceptions.PlusFatalException;
import com.programm.projects.plus.core.exceptions.PlusRuntimeException;
import com.programm.projects.plus.core.lifecycle.AbstractObservableLifecycle;
import com.programm.projects.plus.core.lifecycle.IChainableLifecycle;
import com.programm.projects.plus.core.resource.IResources;
import com.programm.projects.plus.core.settings.EngineSettings;
import com.programm.projects.plus.engine.api.events.EnginePhaseEvent;
import com.programm.projects.plus.engine.api.events.EventStack;
import com.programm.projects.plus.engine.api.exceptions.ExceptionStack;
import com.programm.projects.plus.engine.api.exceptions.IExceptionHandler;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import com.programm.projects.plus.renderer.api.IRenderer;
import com.programm.projects.plus.renderer.api.events.WindowCloseEvent;
import com.programm.projects.plus.resource.api.IResourceManager;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class AbstractEngine extends AbstractObservableLifecycle implements IEngine, IChainableLifecycle {

    private final EventStack eventStack = new EventStack();
    private final EngineSettings settings = new EngineSettings();

    //EXCEPTIONS
    private final ExceptionStack exceptionStack = new ExceptionStack();
    private IExceptionHandler exceptionHandler = IExceptionHandler.PASS;


    //Systems
    private final Map<String, ISubsystem> subsystems = new HashMap<>();
    private IResourceManager resourceManager;
    private IRunLoop runLoop;
    private IRenderer renderer;
    private IGameObjectHandler goh;
    private ICollisionHandler collisionHandler;

    private Scene scene;

    protected EnginePhase phase = EnginePhase.ALIVE;
    private boolean stopRequest = false;



    protected abstract IResourceManager initResourceManager();
    protected abstract IRunLoop initRunLoop();
    protected abstract IRenderer initRenderer();
    protected abstract IGameObjectHandler initGOH();
    protected abstract ICollisionHandler initCollisionHandler();

    /*
     * -- LIFECYCLE METHODS --
     */
    @Override
    protected void onStartup() throws PlusException {
        log.info("[Startup] - Engine");
        changePhase(EnginePhase.STARTING);

        events().listenFor(WindowCloseEvent.class, this::onWindowClose);

        //INIT SYSTEMS
        this.resourceManager = initResourceManager();
        this.runLoop = initRunLoop();
        this.renderer = initRenderer();
        this.goh = initGOH();
        this.collisionHandler = initCollisionHandler();

        //ADD SUBSYSTEMS
        addSubsystem("resources", resourceManager);
        addSubsystem("run-loop", runLoop);
        addSubsystem("renderer", renderer);
        addSubsystem("goh", goh);
        addSubsystem("collision", collisionHandler);


        //LOAD RESOURCES
        resourceManager.loadStaticResources();

        //INIT SETTINGS
        try {
            initSettings();
        }catch (PlusFatalException e){
            throw new PlusFatalException("Failed to initialize Settings!", e);
        }


        //[PREPARE] PHASE
        runLoop.setup(this::update, this);
        renderer.setup(this);
        goh.setup(this, runLoop.info());
        collisionHandler.setup(this);

        scene.init(goh, renderer, this);

        changePhase(EnginePhase.PREPARED);
    }

    private void addSubsystem(String name, ISubsystem subsystem){
        subsystems.put(name, subsystem);
        addLifecycle(subsystem);
    }

    private void initSettings() throws PlusFatalException {
        // ENABLE / DISABLE SUBSYSTEMS
        for(String name : subsystems.keySet()) {
            ISubsystem subsystem = subsystems.get(name);
            String _enabled = "plus.engine." + name + ".enabled";
            boolean enabled = resources().getResource(_enabled)
                    .asBoolean(PlusFatalException.Supply("Failed to read resource [" + _enabled + "]!"));
            boolean success = subsystem.setEnabled(enabled);

            if(!enabled){
                if(success) {
                    log.debug("[" + subsystem.getClass().getSimpleName() + "]: DISABLED");
                }
                else {
                    log.warn("[" + subsystem.getClass().getSimpleName() + "]: Could not be DISABLED!");
                }
            }
        }

        //Init Window Settings
        String title = resources().getResource("plus.game.window.title")
                .asString(PlusFatalException.Supply("Failed to read resource [plus.game.window.title]!"));
        settings().window().setTitle(title);

        int width = resources().getResource("plus.game.window.width")
                .asInt(PlusFatalException.Supply("Failed to read resource [plus.game.window.width]!"));
        settings.window().setWidth(width);

        int height = resources().getResource("plus.game.window.height")
                .asInt(PlusFatalException.Supply("Failed to read resource [plus.game.window.height]!"));
        settings.window().setHeight(height);

        //Init Run loop
        int runLoopSyncFps = resources().getResource("plus.engine.run-loop.fps")
                .asInt(PlusFatalException.Supply("Failed to read resource [plus.engine.run-loop.fps]!"));
        runLoop.setSync(runLoopSyncFps);
    }

    @Override
    public void shutdown() throws PlusException{
        if(stopRequest) {
            super.shutdown();
        }
        else {
            //Resolves invalid renderer state !!!
            //Renderer thread must not call the super.shutdown() as it could enter invalid peer state
            stopRequest = true;
        }
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
            try {
                shutdown();
            } catch (PlusException e){
                throw new PlusRuntimeException("Failed to shut down on stop request!", e);
            }
            return;
        }

        //Handle events
        eventStack.pollEvents();

        //Update all objects
        goh.update();


        try {
            exceptionStack.handle();
        } catch (PlusException e){
            fatalException(e);
        }

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
    public void handleException(IThrowableMethod method) {
        try {
            method.run();
        }
        catch (PlusException e){
            exceptionStack.add(e);
        }
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
    public IRenderContext renderer() {
        return renderer;
    }

    @Override
    public ICollisionContext collision() {
        return collisionHandler;
    }

    @Override
    public EnginePhase phase() {
        return phase;
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void setExceptionHandler(IExceptionHandler handler) {
        exceptionHandler = handler;
    }

    private void fatalException(PlusException e1){
        try {
            super.shutdown();
        }
        catch (PlusException e2){
            try {
                exceptionHandler.handle(e2);
            }
            catch (PlusException e3) {
                log.error("Unhandled Exception when shutting down on Fatal Exception!", e3);
            }
        }

        try {
            exceptionHandler.handle(e1);
        }
        catch (PlusException e2){
            throw new PlusRuntimeException(e2);
        }
    }
}
