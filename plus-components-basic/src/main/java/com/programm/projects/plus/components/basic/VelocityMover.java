package com.programm.projects.plus.components.basic;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.IGameContext;
import com.programm.projects.plus.core.IUpdatableComponent;
import com.programm.projects.plus.core.components.Mover;
import com.programm.projects.plus.maths.Vector2f;

public class VelocityMover implements IUpdatableComponent {

    private final Vector2f velocity;

    public VelocityMover(Vector2f velocity) {
        this.velocity = velocity;
    }

    @Override
    public void update(IGameContext context) {
        GameObject object = context.getObject();

        Mover mover = object.getComponent(Mover.class);
        mover.move(velocity);
    }
}
