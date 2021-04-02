package com.programm.plus.maths;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Matrixf {

    public static Matrixf OfSize(int width, int height){
        float[][] data = new float[height][width];
        return new Matrixf(data);
    }

    public static Matrixf Transposed(Matrixf mat){
        float[][] data = new float[mat.width][mat.height];

        for(int y=0;y<mat.height;y++){
            for(int x=0;x<mat.width;x++){
                data[x][y] = mat.data[y][x];
            }
        }

        return new Matrixf(data);
    }

    public static Matrixf multiply(Matrixf m1, Matrixf m2){
        if(m1.width != m2.height) throw new IllegalArgumentException("Matrices must have compatible sizes. Width1 and Height2 must be the same.");

        int nWidth = m2.width;
        int nHeight = m1.height;

        float[][] mat = new float[nHeight][nWidth];

        for(int y=0;y<m1.height;y++){
            Vectorf row = m1.getRowAsVector(y);

            for(int x=0;x<m2.width;x++){
                Vectorf col = m2.getColumnAsVector(x);

                float dot = row.dot(col);

                mat[y][x] = dot;
            }
        }

        return new Matrixf(mat);
    }

    public static Vectorf multiply(Matrixf m, Vectorf v){
        if(m.width != v.data.length) throw new IllegalArgumentException("Matrices must have compatible sizes. Width1 and Height2 must be the same.");

        float[] data = new float[m.height];

        for(int i=0;i<m.height;i++){
            Vectorf row = m.getRowAsVector(i);

            data[i] = row.dot(v);
        }

        return new Vectorf(data);
    }

    public static Matrixf multiplyLastTransposed(Vectorf v1, Vectorf v2){
        int width = v2.data.length;
        int height = v1.data.length;

        float[][] data = new float[height][width];

        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                data[y][x] = v1.data[y] * v2.data[x];
            }
        }

        return new Matrixf(data);
    }

    public static Matrixf asMatrix(Vectorf v){
        float[][] data = new float[v.data.length][1];

        for(int i=0;i<v.data.length;i++){
            data[i][0] = v.data[i];
        }

        return new Matrixf(data);
    }


    private int width, height;
    float[][] data;

    public Matrixf(@NonNull float[][] data){
        Checks.rowLengthsCheck(data);

        this.width = data[0].length;
        this.height = data.length;
        this.data = data;
    }

    public Matrixf toIdentity(){
        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                data[y][x] = (x == y) ? 1 : 0;
            }
        }

        return this;
    }

    public Matrixf toZero(){
        return toValue(0);
    }

    public Matrixf toValue(float value){
        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                data[y][x] = value;
            }
        }

        return this;
    }

    public Matrixf clone(){
        float[][] mat = new float[height][width];

        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                mat[y][x] = data[y][x];
            }
        }

        return new Matrixf(mat);
    }

    public float get(int x, int y){
        Checks.accessCheck(x, width);
        Checks.accessCheck(y, height);

        return data[y][x];
    }

    public Matrixf set(int x, int y, float value){
        Checks.accessCheck(x, width);
        Checks.accessCheck(y, height);

        data[y][x] = value;

        return this;
    }

    public float[] getColumn(int num){
        Checks.accessCheck(num, width);

        float[] col = new float[height];

        for(int i=0;i<data.length;i++){
            col[i] = data[i][num];
        }

        return col;
    }

    public Vectorf getColumnAsVector(int num){
        float[] col = getColumn(num);
        return new Vectorf(col);
    }

    public float[] getRow(int num){
        Checks.accessCheck(num, height);

        float[] row = new float[width];

        System.arraycopy(data[num], 0, row, 0, width);

        return row;
    }

    public Vectorf getRowAsVector(int num){
        float[] row = getRow(num);
        return new Vectorf(row);
    }

    public Matrixf add(float[][] data){
        Checks.lengthCheck(this.data, data);

        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                this.data[y][x] += data[y][x];
            }
        }

        return this;
    }

    public Matrixf add(Matrixf m){
        return add(m.data);
    }

    public Matrixf sub(float[][] data){
        Checks.lengthCheck(this.data, data);

        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                this.data[y][x] -= data[y][x];
            }
        }

        return this;
    }

    public Matrixf sub(Matrixf m){
        return sub(m.data);
    }

    public Matrixf mul(float[][] data){
        Checks.lengthCheck(this.data, data);

        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                this.data[y][x] *= data[y][x];
            }
        }

        return this;
    }

    public Matrixf mulf(float m){
        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                this.data[y][x] *= m;
            }
        }

        return this;
    }

    public Matrixf mul(Matrixf m){
        return mul(m.data);
    }


}
