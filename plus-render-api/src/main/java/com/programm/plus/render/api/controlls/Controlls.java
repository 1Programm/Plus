package com.programm.plus.render.api.controlls;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Controlls {

    private KeyboardControll keyboard;
    private MouseControll mouse;

    public boolean isKeyboardEnabled(){
        return keyboard != null;
    }

    public boolean isMouseEnabled(){
        return mouse != null;
    }

}
