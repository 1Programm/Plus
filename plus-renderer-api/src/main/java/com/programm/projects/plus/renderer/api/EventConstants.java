package com.programm.projects.plus.renderer.api;

interface EventConstants {

    interface Mouse {
        //OFFSETS
        int MAX = 3;

        int MOUSE_BUTTON_LEFT = 0;
        int MOUSE_BUTTON_MIDDLE = 1;
        int MOUSE_BUTTON_RIGHT = 2;
    }

    interface Keyboard {
        //OFFSETS
        int OFF_NUMBER      = 0;
        int OFF_ABC         = 10;
        int OFF_SYSTEM      = 36;
        int MAX             = 59;

        //NUMBERS
        int KEY_0           = OFF_NUMBER + 0;
        int KEY_1           = OFF_NUMBER + 1;
        int KEY_2           = OFF_NUMBER + 2;
        int KEY_3           = OFF_NUMBER + 3;
        int KEY_4           = OFF_NUMBER + 4;
        int KEY_5           = OFF_NUMBER + 5;
        int KEY_6           = OFF_NUMBER + 6;
        int KEY_7           = OFF_NUMBER + 7;
        int KEY_8           = OFF_NUMBER + 8;
        int KEY_9           = OFF_NUMBER + 9;

        //ABC
        int KEY_A           = OFF_ABC + 0;
        int KEY_B           = OFF_ABC + 1;
        int KEY_C           = OFF_ABC + 2;
        int KEY_D           = OFF_ABC + 3;
        int KEY_E           = OFF_ABC + 4;
        int KEY_F           = OFF_ABC + 5;
        int KEY_G           = OFF_ABC + 6;
        int KEY_H           = OFF_ABC + 7;
        int KEY_I           = OFF_ABC + 8;
        int KEY_J           = OFF_ABC + 9;
        int KEY_K           = OFF_ABC + 10;
        int KEY_L           = OFF_ABC + 11;
        int KEY_M           = OFF_ABC + 12;
        int KEY_N           = OFF_ABC + 13;
        int KEY_O           = OFF_ABC + 14;
        int KEY_P           = OFF_ABC + 15;
        int KEY_Q           = OFF_ABC + 16;
        int KEY_R           = OFF_ABC + 17;
        int KEY_S           = OFF_ABC + 18;
        int KEY_T           = OFF_ABC + 19;
        int KEY_U           = OFF_ABC + 20;
        int KEY_V           = OFF_ABC + 21;
        int KEY_W           = OFF_ABC + 22;
        int KEY_X           = OFF_ABC + 23;
        int KEY_Y           = OFF_ABC + 24;
        int KEY_Z           = OFF_ABC + 25;

        //SYSTEM KEYS
        int KEY_ESCAPE      = OFF_SYSTEM + 0;
        int KEY_ENTER       = OFF_SYSTEM + 1;
        int KEY_SPACE       = OFF_SYSTEM + 2;
        int KEY_CONTROL     = OFF_SYSTEM + 3;
        int KEY_DELETE      = OFF_SYSTEM + 4;
        int KEY_SHIFT       = OFF_SYSTEM + 5;
        int KEY_CAPS_LOCK   = OFF_SYSTEM + 6;

        int KEY_UP          = OFF_SYSTEM + 7;
        int KEY_DOWN        = OFF_SYSTEM + 8;
        int KEY_LEFT        = OFF_SYSTEM + 9;
        int KEY_RIGHT       = OFF_SYSTEM + 10;

        int KEY_F1          = OFF_SYSTEM + 11;
        int KEY_F2          = OFF_SYSTEM + 12;
        int KEY_F3          = OFF_SYSTEM + 13;
        int KEY_F4          = OFF_SYSTEM + 14;
        int KEY_F5          = OFF_SYSTEM + 15;
        int KEY_F6          = OFF_SYSTEM + 16;
        int KEY_F7          = OFF_SYSTEM + 17;
        int KEY_F8          = OFF_SYSTEM + 18;
        int KEY_F9          = OFF_SYSTEM + 19;
        int KEY_F10         = OFF_SYSTEM + 20;
        int KEY_F11         = OFF_SYSTEM + 21;
        int KEY_F12         = OFF_SYSTEM + 22;



        static int getNumberKey(int number){
            if(number < 0 || number >= 10){
                throw new IllegalArgumentException("Number key must be between 0-9!");
            }

            return OFF_NUMBER + number;
        }

        static int getAbcKey(int abc){
            if(abc < 0 || abc >= 26){
                throw new IllegalArgumentException("Abc key must be between 0-25!");
            }

            return OFF_ABC + abc;
        }
    }

}
