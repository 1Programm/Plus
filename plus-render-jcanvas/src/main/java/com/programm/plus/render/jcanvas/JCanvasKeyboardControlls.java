package com.programm.plus.render.jcanvas;

import com.programm.plus.render.api.controlls.KeyboardControll;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class JCanvasKeyboardControlls extends KeyAdapter implements KeyboardControll {

    private List<Integer> keys;

    public JCanvasKeyboardControlls(){
        this.keys = new ArrayList<>();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(!keys.contains(code)){
            keys.add(code);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(keys.contains(code)){
            keys.remove(keys.indexOf(code));
        }
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        return keys.contains(keyCode);
    }
}
