package com.programm.projects.plus.renderer.swing;

import com.programm.projects.plus.core.events.IEventDispatcher;
import com.programm.projects.plus.renderer.api.IWindow;
import com.programm.projects.plus.renderer.api.events.*;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

@Slf4j
public class SwingWindow implements IWindow, ComponentListener, WindowListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private final JFrame frame;
    private final IEventDispatcher eventDispatcher;
    final SwingCanvas canvas;
    private final SwingMouse mouse;
    private final SwingKeyboard keyboard;


    public SwingWindow(String title, int width, int height, IEventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;

        this.frame = new JFrame(title);
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.canvas = new SwingCanvas();
        this.canvas.setPreferredSize(new Dimension(width, height));
        this.frame.add(canvas);
        this.frame.pack();

        this.frame.setLocationRelativeTo(null);

        this.frame.addWindowListener(this);
        this.canvas.addKeyListener(this);
        this.canvas.addMouseListener(this);
        this.canvas.addMouseMotionListener(this);
        this.canvas.addMouseWheelListener(this);

        this.mouse = new SwingMouse();
        this.keyboard = new SwingKeyboard();
    }

    @Override
    public void startup() {
        log.info("[Startup] Swing Window");
    }

    @Override
    public void shutdown() {
        log.info("[Shutdown] Swing Window");
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






    //COMPONENT LISTENER ---------------
    @Override
    public void componentResized(ComponentEvent e) {
        //WINDOW RESIZE EVENT
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //WINDOW MOVE EVENT
    }

    @Override
    public void componentShown(ComponentEvent e) { }

    @Override
    public void componentHidden(ComponentEvent e) { }

    //WINDOW LISTENER ---------------
    @Override
    public void windowOpened(WindowEvent e) { }

    @Override
    public void windowClosing(WindowEvent e) { // On Window x Pressed
        WindowCloseEvent closeEvent = new WindowCloseEvent(this);
        eventDispatcher.dispatch(closeEvent);
    }

    @Override
    public void windowClosed(WindowEvent e) { }

    @Override
    public void windowIconified(WindowEvent e) { }

    @Override
    public void windowDeiconified(WindowEvent e) { }

    @Override
    public void windowActivated(WindowEvent e) { }

    @Override
    public void windowDeactivated(WindowEvent e) { }


    //KEY LISTENER ---------------

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = Utils.parseSwingKeyCode(e.getKeyCode());

        int count = keyboard.keys[keyCode]++;
        KeyPressedEvent event = new KeyPressedEvent(keyboard, keyCode, count);
        eventDispatcher.dispatch(event);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = Utils.parseSwingKeyCode(e.getKeyCode());

        keyboard.keys[keyCode] = 0;
        KeyReleasedEvent event = new KeyReleasedEvent(keyboard, keyCode);
        eventDispatcher.dispatch(event);
    }

    //MOUSE LISTENER ---------------
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        int button = Utils.parseSwingMouseButton(e.getButton());

        mouse.buttonPressed[button] = true;
        mouse.dragX = e.getX();
        mouse.dragY = e.getY();

        MousePressedEvent event = new MousePressedEvent(mouse, button);
        eventDispatcher.dispatch(event);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int button = Utils.parseSwingMouseButton(e.getButton());

        mouse.buttonPressed[button] = false;

        MouseReleasedEvent event = new MouseReleasedEvent(mouse, button);
        eventDispatcher.dispatch(event);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        mouse.x = e.getX();
        mouse.y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse.x = e.getX();
        mouse.y = e.getY();

        MouseMovedEvent event = new MouseMovedEvent(mouse);
        eventDispatcher.dispatch(event);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int scrollAmount = e.getUnitsToScroll();
        mouse.scroll += scrollAmount;

        MouseScrolledEvent event = new MouseScrolledEvent(mouse, scrollAmount);
        eventDispatcher.dispatch(event);
    }
}
