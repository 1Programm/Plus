package com.programm.plus.render.jcanvas;

import com.programm.plus.render.api.GameWindow;
import com.programm.plus.render.api.controlls.Controlls;

import javax.swing.*;
import java.awt.*;

public class JGameWindow extends GameWindow {

    private boolean initialized;

    private JFrame frame;
    private Controlls controlls;

    private WindowListener windowListener;

    public JGameWindow(JFrame frame, Canvas canvas) {
        this.frame = frame;

        JCanvasKeyboardControlls keyboardControlls = new JCanvasKeyboardControlls();
        JCanvasMouseControlls mouseControlls = new JCanvasMouseControlls();

        canvas.addKeyListener(keyboardControlls);
        canvas.addMouseListener(mouseControlls);

        //Not finished - maybe an option to enable mouse Motion
        canvas.addMouseMotionListener(mouseControlls);

        this.controlls.setKeyboard(keyboardControlls);
        this.controlls.setMouse(mouseControlls);

        this.windowListener = new WindowListener();
        this.frame.addWindowListener(windowListener);
    }

    @Override
    protected Controlls initControlls() {
        this.controlls = new Controlls();
        return controlls;
    }

    @Override
    public void setTitle(String title) {
        frame.setTitle(title);
    }

    @Override
    public void setWidth(int width) {
        frame.setSize(width, getHeight());
    }

    @Override
    public void setHeight(int height) {
        frame.setSize(getWidth(), height);
    }

    @Override
    public void setVisible(boolean visible) {
        if(visible) initialized = true;

        frame.setVisible(visible);
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
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public boolean shouldClose() {
        return windowListener.isShouldClose();
    }

    public void dispose(){
        frame.dispose();
    }
}
