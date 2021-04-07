package com.programm.projects.plus.renderer.swing;

import com.programm.projects.plus.core.IGameContext;
import com.programm.projects.plus.core.IObjectBatch;
import com.programm.projects.plus.core.components.IRenderComponent;
import com.programm.projects.plus.core.events.IEventHandler;
import com.programm.projects.plus.core.lifecycle.AbstractObservableLifecycle;
import com.programm.projects.plus.core.lifecycle.IChainableLifecycle;
import com.programm.projects.plus.renderer.api.IRenderer;
import com.programm.projects.plus.renderer.api.WindowInfo;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class SwingRenderer extends AbstractObservableLifecycle implements IRenderer, IChainableLifecycle {

    private final SwingComponentPreparer preparer = new SwingComponentPreparer();
    private SwingWindow window;
    private IObjectBatch renderableBatch;

    // LIFECYCLE METHODS
    @Override
    public void setup(IEventHandler eventHandler, WindowInfo windowInfo) {
        window = new SwingWindow(windowInfo.getTitle(), windowInfo.getWidth(), windowInfo.getHeight(), eventHandler);
        addLifecycle(window);
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
    public void update(IGameContext context) {
        if(window == null) return;
        window.canvas.render(renderableBatch, Color.WHITE);
    }

    @Override
    public void setRenderableBatch(IObjectBatch renderableBatch) {
        this.renderableBatch = renderableBatch;
    }

}
