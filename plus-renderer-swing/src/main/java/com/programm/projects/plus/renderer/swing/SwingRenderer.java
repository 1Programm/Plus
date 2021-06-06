package com.programm.projects.plus.renderer.swing;

import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.IGameContext;
import com.programm.projects.plus.core.IObjectBatch;
import com.programm.projects.plus.core.IRunLoopInfo;
import com.programm.projects.plus.core.components.Camera;
import com.programm.projects.plus.core.components.IRenderComponent;
import com.programm.projects.plus.core.lifecycle.AbstractObservableLifecycle;
import com.programm.projects.plus.core.lifecycle.IChainableLifecycle;
import com.programm.projects.plus.core.settings.WindowSettings;
import com.programm.projects.plus.renderer.api.IRenderer;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class SwingRenderer extends AbstractObservableLifecycle implements IRenderer, IChainableLifecycle {

    private boolean enabled;

    private final SwingComponentPreparer preparer = new SwingComponentPreparer();
    private IEngineContext engineContext;
    private SwingWindow window;
    private IObjectBatch renderableBatch;

    private Camera camera;

    // LIFECYCLE METHODS
    @Override
    public void setup(IEngineContext context) {
        this.engineContext = context;
        WindowSettings settings = context.settings().window();

        window = new SwingWindow(settings.getTitle(), settings.getWidth(), settings.getHeight(), context.events());
        addLifecycle(window);
    }

    @Override
    public void addCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void onStartup() {
        log.info("[Startup] - Swing Renderer");
        window.setVisible(true);
    }

    @Override
    public void onShutdown() {
        log.info("[Shutdown] - Swing Renderer");
        window.setVisible(false);
    }

    //--- IRendererContext
    //METHOD TO INITIALIZE RENDERER SPECIFIC COMPONENTS
    @Override
    public void init(IRenderComponent component) {
        preparer.init(component);
     }

    //OTHER METHODS

    @Override
    public boolean setEnabled(boolean enabled) {
        this.enabled = enabled;
        return true;
    }

    @Override
    public void update() {
        if(!enabled) return;
        if(window == null) return;
        window.canvas.render(engineContext, renderableBatch, Color.WHITE, camera);
    }

    @Override
    public void setRenderableBatch(IObjectBatch renderableBatch) {
        this.renderableBatch = renderableBatch;
    }

}
