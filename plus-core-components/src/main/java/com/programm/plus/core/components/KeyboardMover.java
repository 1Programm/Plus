package com.programm.plus.core.components;

import com.programm.plus.core.GameContext;
import com.programm.plus.core.component.Component;
import com.programm.plus.core.component.UpdateComponent;
import com.programm.plus.maths.Vector2f;
import com.programm.plus.render.api.controlls.KeyboardControll;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.KeyEvent;

@Slf4j
public class KeyboardMover extends UpdateComponent {

    private float speed;

    private int up, down, left, right;

    public KeyboardMover(float speed){
        this.speed = speed;
        this.up = KeyEvent.VK_W;
        this.down = KeyEvent.VK_S;
        this.left = KeyEvent.VK_A;
        this.right = KeyEvent.VK_D;
    }

    public KeyboardMover(float speed, int up, int down, int left, int right){
        this.speed = speed;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    @Override
    public void onUpdate(GameContext gameContext) {
        if(!gameContext.getControlls().isKeyboardEnabled()){
            log.warn("Keyboard is not enabled!");
            return;
        }

        KeyboardControll keyboard = gameContext.getControlls().getKeyboard();

        Vector2f vel = new Vector2f();

        if(keyboard.isKeyPressed(up)){
            vel.setY(-speed);
        }

        if(keyboard.isKeyPressed(down)){
            vel.add(0, speed);
        }

        if(keyboard.isKeyPressed(left)){
            vel.setX(-speed);
        }

        if(keyboard.isKeyPressed(right)){
            vel.add(speed, 0);
        }

        vel.mul(gameContext.getDeltaTime());

        move(vel);
    }

    @Override
    public Component copy() {
        return new KeyboardMover(this.speed, this.up, this.down, this.left, this.right);
    }
}
