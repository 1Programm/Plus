package com.programm.plus.maths;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transform2f {

    private Vector2f position;
    private Vector2f size;
    private float rotation;

    public Transform2f(){
        this.position = new Vector2f();
        this.size = new Vector2f();
        this.rotation = 0;
    }

    public Transform2f(Vector2f position, Vector2f size, float rotation) {
        this.position = position;
        this.size = size;
        this.rotation = rotation;

        if(this.position == null){
            this.position = new Vector2f();
        }
        if(this.size == null){
            this.size = new Vector2f();
        }
    }

    public Transform2f setPosition(float x, float y){
        this.position.set(x, y);
        return this;
    }

    public Transform2f setSize(float width, float height){
        this.size.set(width, height);
        return this;
    }

    @Override
    public Transform2f clone() {
        Vector2f position = this.position.clone();
        Vector2f size = this.size.clone();
        float rotation = this.rotation;

        return new Transform2f(position, size, rotation);
    }
}
