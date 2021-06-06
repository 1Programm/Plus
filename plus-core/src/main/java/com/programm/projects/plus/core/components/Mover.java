package com.programm.projects.plus.core.components;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.IComponent;
import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.maths.Vector1f;
import com.programm.projects.plus.maths.Vector2f;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Mover implements IComponent {

    private IEngineContext engineContext;

    private final Vector2f velocity = new Vector2f();
    private final Vector1f rotVelocity = new Vector1f();

    private boolean updated;

    @Override
    public void init(IEngineContext context) {
        this.engineContext = context;
    }

    public void update(GameObject object) {
        if(!updated) return;

        Transform transform = object.getTransform();

        //Move position
        engineContext.collision().checkMovement(object, velocity);
        transform.position.add(velocity);
        velocity.set(0, 0);

        //Move rotation
        engineContext.collision().checkRotation(object, rotVelocity);
        transform.rotation.add(rotVelocity);
        rotVelocity.set(0);


        updated = false;
    }

    public void move(Vector2f vel){
        move(vel.getX(), vel.getY());
    }

    public void move(float x, float y){
        this.velocity.add(x, y);

        if(x != 0 || y != 0) {
            updated = true;
        }
    }

    public void rotate(float rotVelocity){
        this.rotVelocity.add(rotVelocity);

        if(rotVelocity != 0) {
            //updated = true;
        }
    }
}
