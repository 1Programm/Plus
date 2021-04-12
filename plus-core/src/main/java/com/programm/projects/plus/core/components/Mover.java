package com.programm.projects.plus.core.components;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.IComponent;
import com.programm.projects.plus.maths.Vector2f;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Mover implements IComponent {

    private final Vector2f velocity = new Vector2f();
    private float rotVelocity;

    public void update(GameObject object) {
        Transform transform = object.getTransform();

        //Move position
        Vector2f position = transform.position;
        position.add(velocity);
        velocity.set(0, 0);

        //Move rotation
        transform.rotation += rotVelocity;
        rotVelocity = 0;
    }

    public void move(Vector2f vel){
        this.velocity.add(vel);
    }

    public void move(float x, float y){
        this.velocity.add(x, y);
    }

    public void rotate(float rotVel){
        rotVelocity += rotVel;
    }
}
