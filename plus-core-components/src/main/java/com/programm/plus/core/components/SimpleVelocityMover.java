package com.programm.plus.core.components;

import com.programm.plus.core.GameContext;
import com.programm.plus.core.component.Component;
import com.programm.plus.core.component.UpdateComponent;
import com.programm.plus.maths.Vector2f;
import lombok.Getter;

@Getter
public class SimpleVelocityMover extends UpdateComponent {

    private Vector2f velocity;

    public SimpleVelocityMover(Vector2f velocity){
        this.velocity = velocity;
    }

    @Override
    public void onUpdate(GameContext gameContext) {
        Vector2f vel = Vector2f.Mul(velocity, gameContext.getDeltaTime());
        move(vel);
    }

    @Override
    public Component copy() {
        return new SimpleVelocityMover(this.velocity.clone());
    }
}
