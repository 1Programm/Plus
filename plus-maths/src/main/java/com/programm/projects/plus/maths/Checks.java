package com.programm.projects.plus.maths;

class Checks {

    static void accessCheck(int pos, int max){
        if(pos < 0 || pos >= max) throw new IllegalArgumentException("Value is not in range (0 - " + (max-1) + ")");
    }

    static void lengthCheck(float[] data1, float[] data2){
        if(data1.length != data2.length) throw new IllegalArgumentException("Sizes of the two Data must be the same.");
    }

    static void lengthCheck(float[] data1, Vectorf data2){
        lengthCheck(data1, data2.data);
    }

    static void lengthCheck(Vectorf data1, float[] data2){
        lengthCheck(data1.data, data2);
    }

    static void lengthCheck(Vectorf data1, Vectorf data2){
        lengthCheck(data1.data, data2.data);
    }

    static void lengthCheck(float[][] data1, float[][] data2){
        if(data1.length != data2.length) throw new IllegalArgumentException("Sizes of the two Data must be the same.");
    }

    static void lengthCheck(float[][] data1, Matrixf data2){
        lengthCheck(data1, data2.data);
    }

    static void lengthCheck(Matrixf data1, float[][] data2){
        lengthCheck(data1.data, data2);
    }

    static void lengthCheck(Matrixf data1, Matrixf data2){
        lengthCheck(data1.data, data2.data);
    }

    static void rowLengthsCheck(float[][] data){
        int l = -1;

        for(float[] d : data) {
            if(l == -1){
                l = d.length;
            }

            if(l != d.length){
                throw new IllegalArgumentException("The Data must have the same row - lengths.");
            }
        }
    }

}
