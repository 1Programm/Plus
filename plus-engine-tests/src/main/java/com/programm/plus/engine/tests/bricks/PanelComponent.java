package com.programm.plus.engine.tests.bricks;

import com.programm.plus.core.GameContext;
import com.programm.plus.core.component.UpdateComponent;
import com.programm.plus.render.api.controlls.KeyboardControll;

import java.awt.event.KeyEvent;

public class PanelComponent extends UpdateComponent {

    private float speed;

    public PanelComponent(float speed) {
        this.speed = speed;
    }

    @Override
    public void onUpdate(GameContext context) {
        KeyboardControll keyboard = context.getControlls().getKeyboard();
        float vx = 0;

        if(keyboard.isKeyPressed(KeyEvent.VK_A)){
            vx += -speed;
        }

        if(keyboard.isKeyPressed(KeyEvent.VK_D)){
            vx += speed;
        }

        if(vx != 0) {
            vx *= context.getDeltaTime();

            if(transform.getPosition().getX() + vx < 0
                    || transform.getPosition().getX() + transform.getSize().getX() + vx > context.getWindow().getWidth()){
                vx = 0;
            }
        }

        move(vx, 0);
    }

}
