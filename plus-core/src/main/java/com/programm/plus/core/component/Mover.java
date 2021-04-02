package com.programm.plus.core.component;

import com.programm.plus.core.GameContext;
import com.programm.plus.maths.Vector2f;

public class Mover extends UpdateComponent {

    private Vector2f velocity;
    private boolean updated;

    public Mover(){
        this.velocity = new Vector2f();
        this.updated = false;
    }

    @Override
    public void onUpdate(GameContext context) {
        if(!updated) return;

        transform.getPosition().add(velocity);

        velocity.set(0, 0);
        updated = false;
    }

    public void addVelocity(Vector2f vel){
        addVelocity(vel.getX(), vel.getY());
    }

    public void addVelocity(float vx, float vy){
        this.velocity.add(vx, vy);
        this.updated = true;
    }

    @Override
    public Mover copy() {
        return new Mover();
    }
}
