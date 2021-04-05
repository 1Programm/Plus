package com.programm.projects.plus.renderer.swing;

import com.programm.projects.core.IGameContext;
import com.programm.projects.core.events.IEventDispatcher;
import com.programm.projects.core.lifecycle.AbstractObservableLifecycle;
import com.programm.projects.core.lifecycle.IChainableLifecycle;
import com.programm.projects.core.IObjectBatch;
import com.programm.projects.plus.renderer.api.IRenderer;
import com.programm.projects.plus.renderer.api.IWindow;
import com.programm.projects.plus.renderer.api.events.WindowCloseEvent;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class SwingRenderer extends AbstractObservableLifecycle implements IRenderer, IChainableLifecycle {

    private SwingWindow window;
    private IEventDispatcher eventDispatcher;
    private IObjectBatch renderableBatch;

    @Override
    public void init(IEventDispatcher eventDispatcher) {
        if(window == null){
            throw new IllegalStateException("Window is not created yet!");
        }

        this.eventDispatcher = eventDispatcher;
        this.window.addOnCloseListener(this::onWindowClose);
    }

    private void onWindowClose(IWindow window){
        WindowCloseEvent closeEvent = new WindowCloseEvent(window);
        eventDispatcher.dispatchEvent(closeEvent);
    }

    @Override
    public void onStartup() {
        log.info("[Startup] - Swing Renderer");
        createWindow("Engine", 600, 500);
        window.setVisible(true);
    }

    @Override
    public void onShutdown() {
        log.info("[Shutdown] - Swing Renderer");
        window.setVisible(false);
    }

    @Override
    public void update(IGameContext context) {
        if(window == null) return;
        window.canvas.render(renderableBatch, Color.WHITE);
    }

    @Override
    public void createWindow(String title, int width, int height) {
        if(window != null){
            log.error("Window has already been created!");
            return;
        }

        window = new SwingWindow(title, width, height);
        addLifecycle(window);
    }

    @Override
    public IWindow getWindow() {
        return window;
    }

    @Override
    public void setRenderableBatch(IObjectBatch renderableBatch) {
        this.renderableBatch = renderableBatch;
    }
}
