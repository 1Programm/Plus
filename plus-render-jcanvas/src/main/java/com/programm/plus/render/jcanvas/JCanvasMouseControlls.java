package com.programm.plus.render.jcanvas;

import com.programm.plus.render.api.controlls.MouseControll;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Slf4j
public class JCanvasMouseControlls extends MouseAdapter implements MouseControll {

    private static final int MAX_BUTTONS = 3;

    private int x, y;

    private boolean[] buttons;

    public JCanvasMouseControlls(){
        buttons = new boolean[MAX_BUTTONS];
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        int mouseButton = e.getButton();

        if(!check(mouseButton)) return;

        buttons[mouseButton] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        int mouseButton = e.getButton();

        if(!check(mouseButton)) return;

        buttons[mouseButton] = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public boolean isMouseButtonPressed(int mouseButton) {
        if(!check(mouseButton)) return false;

        return buttons[mouseButton];
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    private boolean check(int mouseButton){
        if(mouseButton < 0 || mouseButton >= MAX_BUTTONS){
            log.warn("Mouse button '{}' is out of bounds: {} - {}", mouseButton, 0, (MAX_BUTTONS-1));
            return false;
        }

        return true;
    }
}
