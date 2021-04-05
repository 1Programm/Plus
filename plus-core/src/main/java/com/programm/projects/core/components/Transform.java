package com.programm.projects.core.components;

import com.programm.projects.core.IComponent;
import com.programm.projects.plus.maths.Vector2f;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Transform implements IComponent {

    final Vector2f position;
    final Vector2f scale;
    float rotation;

    public Transform(float x, float y, float scaleX, float scaleY, float rotation){
        this.position = new Vector2f(x, y);
        this.scale = new Vector2f(scaleX, scaleY);
        this.rotation = rotation;
    }

    public float getX(){
        return position.getX();
    }

    public float getY(){
        return position.getY();
    }

    public float getXScale(){
        return scale.getX();
    }

    public float getYScale(){
        return scale.getY();
    }

    public float getRotation() {
        return rotation;
    }
}
