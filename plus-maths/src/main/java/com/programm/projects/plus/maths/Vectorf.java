package com.programm.projects.plus.maths;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Vectorf {

    public static Vectorf OfSize(int length){
        float[] data = new float[length];
        return new Vectorf(data);
    }

    public static Vectorf Add(Vectorf v1, float[] v2){
        Checks.lengthCheck(v1, v2);

        int length = v1.data.length;
        float[] nData = new float[length];

        for(int i=0;i<length;i++){
            nData[i] = v1.data[i] + v2[i];
        }

        return new Vectorf(nData);
    }

    public static Vectorf Add(Vectorf v1, Vectorf v2){
        return Add(v1, v2.data);
    }

    public static Vectorf Sub(Vectorf v1, float[] v2){
        Checks.lengthCheck(v1, v2);

        int length = v1.data.length;
        float[] nData = new float[length];

        for(int i=0;i<length;i++){
            nData[i] = v1.data[i] - v2[i];
        }

        return new Vectorf(nData);
    }

    public static Vectorf Sub(Vectorf v1, Vectorf v2){
        return Sub(v1, v2.data);
    }

    public static Vectorf Mul(Vectorf v1, float[] v2){
        Checks.lengthCheck(v1, v2);

        int length = v1.data.length;
        float[] nData = new float[length];

        for(int i=0;i<length;i++){
            nData[i] = v1.data[i] * v2[i];
        }

        return new Vectorf(nData);
    }

    public static Vectorf Mul(Vectorf v1, Vectorf v2){
        return Mul(v1, v2.data);
    }

    public static Vectorf Mul(Vectorf v1, float m){
        int length = v1.data.length;
        float[] nData = new float[length];

        for(int i=0;i<length;i++){
            nData[i] = v1.data[i] * m;
        }

        return new Vectorf(nData);
    }

    public static Vectorf Div(Vectorf v1, float[] v2){
        Checks.lengthCheck(v1, v2);

        int length = v1.data.length;
        float[] nData = new float[length];

        for(int i=0;i<length;i++){
            nData[i] = v1.data[i] / v2[i];
        }

        return new Vectorf(nData);
    }

    public static Vectorf Div(Vectorf v1, Vectorf v2){
        return Div(v1, v2.data);
    }

    public static Vectorf Div(Vectorf v1, float d){
        int length = v1.data.length;
        float[] nData = new float[length];

        for(int i=0;i<length;i++){
            nData[i] = v1.data[i] / d;
        }

        return new Vectorf(nData);
    }

    float[] data;

    public Vectorf(float... data){
        this.data = data;
    }

    public float get(int pos){
        Checks.accessCheck(pos, data.length);
        return data[pos];
    }

    public Vectorf set(int pos, float value){
        Checks.accessCheck(pos, data.length);
        data[pos] = value;

        return this;
    }

    public float lengthSqrt(){
        float l = 0;

        for(float d : data){
            l += d * d;
        }

        return l;
    }

    public float length(){
        return (float) Math.sqrt(lengthSqrt());
    }

    public Vectorf clone(){
        float[] nData = new float[data.length];
        System.arraycopy(data, 0, nData, 0, data.length);
        return new Vectorf(nData);
    }

    public float dot(Vectorf other){
        Checks.lengthCheck(data, other);

        float product = 0;

        for(int i=0;i<data.length;i++){
            product += this.data[i] * other.data[i];
        }

        return product;
    }

    public Vectorf add(float... data){
        Checks.lengthCheck(this.data, data);

        for(int i=0;i<this.data.length;i++){
            this.data[i] += data[i];
        }

        return this;
    }

    public Vectorf add(Vectorf other){
        return add(other.data);
    }

    public Vectorf sub(float... data){
        Checks.lengthCheck(this.data, data);

        for(int i=0;i<this.data.length;i++){
            this.data[i] -= data[i];
        }

        return this;
    }

    public Vectorf sub(Vectorf other){
        return sub(other.data);
    }

    public Vectorf mul(float... data){
        Checks.lengthCheck(this.data, data);

        for(int i=0;i<this.data.length;i++){
            this.data[i] *= data[i];
        }

        return this;
    }

    public Vectorf mulf(float m){
        for(int i=0;i<this.data.length;i++){
            this.data[i] *= m;
        }

        return this;
    }

    public Vectorf mul(Vectorf other){
        return mul(other.data);
    }

    public Vectorf div(float... data){
        Checks.lengthCheck(this.data, data);

        for(int i=0;i<this.data.length;i++){
            this.data[i] /= data[i];
        }

        return this;
    }

    public Vectorf div(Vectorf other){
        return div(other.data);
    }

}
