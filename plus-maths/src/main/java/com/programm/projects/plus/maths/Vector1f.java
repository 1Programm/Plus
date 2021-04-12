package com.programm.projects.plus.maths;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Vector1f {

    public static float LengthSqrt(Vector1f v){
        return v.val * v.val;
    }

    public static float Length(Vector1f v){
        return (float)(Math.sqrt(LengthSqrt(v)));
    }

    public static Vector1f Normalized(Vector1f v){
        return v.clone().normalize();
    }

    public static float Dot(Vector1f v1, Vector1f v2){
        return v1.dot(v2);
    }

    public static Vector1f Add(Vector1f v, float val){
        return v.clone().add(val);
    }

    public static Vector1f Add(Vector1f v1, Vector1f v2){
        return Add(v1, v2.val);
    }

    public static Vector1f Sub(Vector1f v, float val){
        return v.clone().sub(val);
    }

    public static Vector1f Sub(Vector1f v1, Vector1f v2){
        return Sub(v1, v2.val);
    }

    public static Vector1f Mul(Vector1f v, float val){
        return v.clone().mul(val);
    }

    public static Vector1f Mul(Vector1f v1, Vector1f v2){
        return Mul(v1, v2.val);
    }

    public static Vector1f Div(Vector1f v, float val){
        return v.clone().div(val);
    }

    public static Vector1f Div(Vector1f v1, Vector1f v2){
        return Div(v1, v2.val);
    }



    private float val;

    @Override
    public Vector1f clone(){
        return new Vector1f(val);
    }

    public float lengthSqrt(){
        return val * val;
    }

    public float length(){
        return (float)(Math.sqrt(lengthSqrt()));
    }

    public Vector1f normalize(){
        return div(length());
    }

    public float dot(Vector1f v){
        return this.val * v.val;
    }

    public Vector1f set(float val){
        setVal(val);

        return this;
    }

    public Vector1f set(Vector1f v){
        return set(v.val);
    }

    public Vector1f add(float val){
        this.val += val;

        return this;
    }

    public Vector1f add(Vector1f v){
        return add(v.val);
    }

    public Vector1f sub(float val){
        this.val -= val;

        return this;
    }

    public Vector1f sub(Vector1f v){
        return sub(v.val);
    }

    public Vector1f mul(float val){
        this.val *= val;

        return this;
    }

    public Vector1f mul(Vector1f v){
        return mul(v.val);
    }

    public Vector1f div(float val){
        this.val /= val;

        return this;
    }

    public Vector1f div(Vector1f v){
        return div(v.val);
    }

}
