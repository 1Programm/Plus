package com.programm.plus.core.components;

import com.programm.plus.core.GameContext;
import com.programm.plus.core.component.UpdateComponent;
import com.programm.plus.core.obj.GameObject;
import com.programm.plus.render.api.controlls.MouseControll;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class OnMousePressed extends UpdateComponent {

    private boolean pressed;

    private Runnable action1;
    private Consumer<GameObject> action2;
    private BiConsumer<GameContext, GameObject> action3;

    public OnMousePressed(Runnable onMousePressed) {
        this.action1 = onMousePressed;
    }

    public OnMousePressed(Consumer<GameObject> onMousePressed) {
        this.action2 = onMousePressed;
    }

    public OnMousePressed(BiConsumer<GameContext, GameObject> onMousePressed){
        this.action3 = onMousePressed;
     }

    @Override
    public void onUpdate(GameContext context) {
        MouseControll mouse = context.getControlls().getMouse();

        boolean _pressed = mouse.isMouseButtonPressed(1);

        if(pressed && _pressed) return;

        pressed = _pressed;

        if(pressed) {
            Rectangle rect = new Rectangle((int) transform.getPosition().getX(), (int) transform.getPosition().getY(),
                    (int) transform.getSize().getX(), (int) transform.getSize().getY());

            if(rect.contains(mouse.getX(), mouse.getY())){
                ComponentUtils.runEither(action1, action2, action3, context, gameObject);
            }
        }
    }
}
