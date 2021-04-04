package main;

import com.programm.projects.core.IGameContext;
import com.programm.projects.core.lifecycle.AbstractObservableLifecycle;
import com.programm.projects.core.lifecycle.IChainableLifecycle;
import com.programm.projects.plus.renderer.api.IRenderableObjectBatch;
import com.programm.projects.plus.renderer.api.IRenderer;
import com.programm.projects.plus.renderer.api.IWindow;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestRenderer extends AbstractObservableLifecycle implements IRenderer, IChainableLifecycle {

    private IWindow window;

    @Override
    public void onStartup() {
        log.info("[Startup] - Test Renderer");
        createWindow("Engine", 600, 500);
        window.setVisible(true);
    }

    @Override
    public void onShutdown() {
        log.info("[Shutdown] - Test Renderer");
        window.setVisible(false);
    }

    @Override
    public void update(IGameContext context) {

    }

    @Override
    public void createWindow(String title, int width, int height) {
        if(window != null){
            log.error("Window has already been created!");
            return;
        }

        window = new TestWindow(title, width, height);
        addLifecycle(window);
    }

    @Override
    public IWindow getWindow() {
        return window;
    }

    @Override
    public void setRenderableObjectBatch(IRenderableObjectBatch objectBatch) {

    }
}
