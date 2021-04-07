package com.programm.projects.plus.core.components;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.IComponent;
import com.programm.projects.plus.maths.Vector2f;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Mover implements IComponent {

    private Vector2f velocity = new Vector2f();

    public void update(GameObject object) {
        Transform transform = object.getTransform();
        Vector2f position = transform.position;
        position.add(velocity);

        velocity.set(0, 0);
    }

    public void move(Vector2f vel){
        this.velocity.add(vel);
    }

    public void move(float x, float y){
        this.velocity.add(x, y);
    }
}
