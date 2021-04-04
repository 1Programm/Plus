package main;

import com.programm.projects.plus.renderer.api.IWindow;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class TestWindow implements IWindow, ComponentListener, WindowListener {

    private final JFrame frame;
    private final List<Consumer<IWindow>> windowResizeListeners = new ArrayList<>();
    private final List<Consumer<IWindow>> windowMoveListeners = new ArrayList<>();
    private final List<Consumer<IWindow>> windowCloseListeners = new ArrayList<>();
    private boolean hasComponentListener;

    public TestWindow(String title, int width, int height) {
        this.frame = new JFrame(title);
        this.frame.setSize(width, height);
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.frame.addWindowListener(this);
    }

    @Override
    public void startup() {
        log.info("[Startup] Test Window");
    }

    @Override
    public void shutdown() {
        log.info("[Shutdown] Test Window");
        frame.dispose();
    }

    @Override
    public String getTitle() {
        return frame.getTitle();
    }

    @Override
    public int getWidth() {
        return frame.getWidth();
    }

    @Override
    public int getHeight() {
        return frame.getHeight();
    }

    @Override
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    @Override
    public void addResizeListener(Consumer<IWindow> listener) {
        windowResizeListeners.add(listener);

        if(!hasComponentListener){
            hasComponentListener = true;
            frame.addComponentListener(this);
        }
    }

    @Override
    public void addMoveListener(Consumer<IWindow> listener) {
        windowMoveListeners.add(listener);

        if(!hasComponentListener){
            hasComponentListener = true;
            frame.addComponentListener(this);
        }
    }

    @Override
    public void addOnCloseListener(Consumer<IWindow> listener) {
        windowCloseListeners.add(listener);
    }

    //COMPONENT LISTENER ---------------
    @Override
    public void componentResized(ComponentEvent e) {
        windowResizeListeners.forEach(l -> l.accept(this));
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        windowMoveListeners.forEach(l -> l.accept(this));
    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    //WINDOW LISTENER ---------------
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) { // On Window x Pressed
        windowCloseListeners.forEach(l -> l.accept(this));
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
