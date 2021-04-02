package com.programm.projects.plus.maths;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Vector3f {

    public static float LengthSqrt(Vector3f v){
        return v.lengthSqrt();
    }

    public static float Length(Vector3f v){
        return (float)(Math.sqrt(LengthSqrt(v)));
    }

    public static Vector3f Normalized(Vector3f v){
        return v.clone().normalize();
    }

    public static float Dot(Vector3f v1, Vector3f v2){
        return v1.dot(v2);
    }

    public static Vector3f Add(Vector3f v, float x, float y, float z){
        return v.clone().add(x, y, z);
    }

    public static Vector3f Add(Vector3f v1, Vector3f v2){
        return Add(v1, v2.x, v2.y, v2.z);
    }

    public static Vector3f Sub(Vector3f v, float x, float y, float z){
        return v.clone().sub(x, y, z);
    }

    public static Vector3f Sub(Vector3f v1, Vector3f v2){
        return Sub(v1, v2.x, v2.y, v2.z);
    }

    public static Vector3f Mul(Vector3f v, float x, float y, float z){
        return v.clone().mul(x, y, z);
    }

    public static Vector3f Mul(Vector3f v, float m){
        return Mul(v, m, m, m);
    }

    public static Vector3f Mul(Vector3f v1, Vector3f v2){
        return Mul(v1, v2.x, v2.y, v2.z);
    }

    public static Vector3f Div(Vector3f v, float x, float y, float z){
        return v.clone().div(x, y, z);
    }

    public static Vector3f Div(Vector3f v, float d){
        return Div(v, d, d, d);
    }

    public static Vector3f Div(Vector3f v1, Vector3f v2){
        return Div(v1, v2.x, v2.y, v2.z);
    }




    private float x, y, z;

    @Override
    public Vector3f clone(){
        return new Vector3f(x, y, z);
    }

    public float lengthSqrt(){
        return x * x + y * y + z * z;
    }

    public float length(){
        return (float)(Math.sqrt(lengthSqrt()));
    }

    public Vector3f normalize(){
        return div(length());
    }

    public float dot(Vector3f v){
        return
                this.x * v.x +
                this.y * v.y +
                this.z * v.z;
    }

    public Vector3f set(float x, float y, float z){
        setX(x);
        setY(y);
        setZ(z);

        return this;
    }

    public Vector3f set(Vector3f v){
        return set(v.x, v.y, v.z);
    }

    public Vector3f add(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;

        return this;
    }

    public Vector3f add(Vector3f v){
        return add(v.x, v.y, v.z);
    }

    public Vector3f sub(float x, float y, float z){
        this.x -= x;
        this.y -= y;
        this.z -= z;

        return this;
    }

    public Vector3f sub(Vector3f v){
        return sub(v.x, v.y, v.z);
    }

    public Vector3f mul(float x, float y, float z){
        this.x *= x;
        this.y *= y;
        this.z *= z;

        return this;
    }

    public Vector3f mul(float m){
        return mul(m, m, m);
    }

    public Vector3f mul(Vector3f v){
        return mul(v.x, v.y, v.z);
    }

    public Vector3f div(float x, float y, float z){
        this.x /= x;
        this.y /= y;
        this.z /= z;

        return this;
    }

    public Vector3f div(float d){
        return div(d, d, d);
    }

    public Vector3f div(Vector3f v){
        return div(v.x, v.y, v.z);
    }

}
