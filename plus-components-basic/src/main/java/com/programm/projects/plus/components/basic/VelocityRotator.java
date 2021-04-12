package com.programm.projects.plus.components.basic;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.IGameContext;
import com.programm.projects.plus.core.IUpdatableComponent;
import com.programm.projects.plus.core.components.Mover;

public class VelocityRotator implements IUpdatableComponent {

    private final float rotVelocity;

    public VelocityRotator(float rotVelocity) {
        this.rotVelocity = rotVelocity;
    }

    @Override
    public void update(IGameContext context) {
        GameObject object = context.getObject();

        Mover mover = object.getComponent(Mover.class);
        mover.rotate((float) (rotVelocity * context.getDelta()));
    }
}
