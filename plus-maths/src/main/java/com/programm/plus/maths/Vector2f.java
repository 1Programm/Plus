package com.programm.plus.maths;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Vector2f {

    public static float LengthSqrt(Vector2f v){
        return v.x * v.x + v.y * v.y;
    }

    public static float Length(Vector2f v){
        return (float)(Math.sqrt(LengthSqrt(v)));
    }

    public static Vector2f Normalized(Vector2f v){
        return v.clone().normalize();
    }

    public static float Dot(Vector2f v1, Vector2f v2){
        return v1.dot(v2);
    }

    public static Vector2f Add(Vector2f v, float x, float y){
        return v.clone().add(x, y);
    }

    public static Vector2f Add(Vector2f v1, Vector2f v2){
        return Add(v1, v2.x, v2.y);
    }

    public static Vector2f Sub(Vector2f v, float x, float y){
        return v.clone().sub(x, y);
    }

    public static Vector2f Sub(Vector2f v1, Vector2f v2){
        return Sub(v1, v2.x, v2.y);
    }

    public static Vector2f Mul(Vector2f v, float x, float y){
        return v.clone().mul(x, y);
    }

    public static Vector2f Mul(Vector2f v, float m){
        return Mul(v, m, m);
    }

    public static Vector2f Mul(Vector2f v1, Vector2f v2){
        return Mul(v1, v2.x, v2.y);
    }

    public static Vector2f Div(Vector2f v, float x, float y){
        return v.clone().div(x, y);
    }

    public static Vector2f Div(Vector2f v, float d){
        return Div(v, d, d);
    }

    public static Vector2f Div(Vector2f v1, Vector2f v2){
        return Div(v1, v2.x, v2.y);
    }




    private float x, y;

    @Override
    public Vector2f clone(){
        return new Vector2f(x, y);
    }

    public float lengthSqrt(){
        return x * x + y * y;
    }

    public float length(){
        return (float)(Math.sqrt(lengthSqrt()));
    }

    public Vector2f normalize(){
        return div(length());
    }

    public float dot(Vector2f v){
        return
                this.x * v.x +
                this.y * v.y;
    }

    public Vector2f set(float x, float y){
        setX(x);
        setY(y);

        return this;
    }

    public Vector2f set(Vector2f v){
        return set(v.x, v.y);
    }

    public Vector2f add(float x, float y){
        this.x += x;
        this.y += y;

        return this;
    }

    public Vector2f add(Vector2f v){
        return add(v.x, v.y);
    }

    public Vector2f sub(float x, float y){
        this.x -= x;
        this.y -= y;

        return this;
    }

    public Vector2f sub(Vector2f v){
        return sub(v.x, v.y);
    }

    public Vector2f mul(float x, float y){
        this.x *= x;
        this.y *= y;

        return this;
    }

    public Vector2f mul(float m){
        return mul(m, m);
    }

    public Vector2f mul(Vector2f v){
        return mul(v.x, v.y);
    }

    public Vector2f div(float x, float y){
        this.x /= x;
        this.y /= y;

        return this;
    }

    public Vector2f div(float d){
        return div(d, d);
    }

    public Vector2f div(Vector2f v){
        return div(v.x, v.y);
    }

}
