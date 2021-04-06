package com.programm.projects.plus.renderer.swing;

import com.programm.projects.plus.renderer.api.events.KeyPressedEvent;
import com.programm.projects.plus.renderer.api.events.MousePressedEvent;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

class Utils {

    public static int parseSwingMouseButton(int button){
        switch (button){
            case MouseEvent.BUTTON1:
                return MousePressedEvent.MOUSE_BUTTON_LEFT;
            case MouseEvent.BUTTON3:
                return MousePressedEvent.MOUSE_BUTTON_MIDDLE;
            case MouseEvent.BUTTON2:
                return MousePressedEvent.MOUSE_BUTTON_RIGHT;

            default:
                return -1;
        }
    }

    public static int parseSwingKeyCode(int keyCode){
        switch (keyCode){
            //NUMBERS
            case KeyEvent.VK_0:
                return KeyPressedEvent.KEY_0;
            case KeyEvent.VK_1:
                return KeyPressedEvent.KEY_1;
            case KeyEvent.VK_2:
                return KeyPressedEvent.KEY_2;
            case KeyEvent.VK_3:
                return KeyPressedEvent.KEY_3;
            case KeyEvent.VK_4:
                return KeyPressedEvent.KEY_4;
            case KeyEvent.VK_5:
                return KeyPressedEvent.KEY_5;
            case KeyEvent.VK_6:
                return KeyPressedEvent.KEY_6;
            case KeyEvent.VK_7:
                return KeyPressedEvent.KEY_7;
            case KeyEvent.VK_8:
                return KeyPressedEvent.KEY_8;
            case KeyEvent.VK_9:
                return KeyPressedEvent.KEY_9;

            //ABC
            case KeyEvent.VK_A:
                return KeyPressedEvent.KEY_A;
            case KeyEvent.VK_B:
                return KeyPressedEvent.KEY_B;
            case KeyEvent.VK_C:
                return KeyPressedEvent.KEY_C;
            case KeyEvent.VK_D:
                return KeyPressedEvent.KEY_D;
            case KeyEvent.VK_E:
                return KeyPressedEvent.KEY_E;
            case KeyEvent.VK_F:
                return KeyPressedEvent.KEY_F;
            case KeyEvent.VK_G:
                return KeyPressedEvent.KEY_G;
            case KeyEvent.VK_H:
                return KeyPressedEvent.KEY_H;
            case KeyEvent.VK_I:
                return KeyPressedEvent.KEY_I;
            case KeyEvent.VK_J:
                return KeyPressedEvent.KEY_J;
            case KeyEvent.VK_K:
                return KeyPressedEvent.KEY_K;
            case KeyEvent.VK_L:
                return KeyPressedEvent.KEY_L;
            case KeyEvent.VK_M:
                return KeyPressedEvent.KEY_M;
            case KeyEvent.VK_N:
                return KeyPressedEvent.KEY_N;
            case KeyEvent.VK_O:
                return KeyPressedEvent.KEY_O;
            case KeyEvent.VK_P:
                return KeyPressedEvent.KEY_P;
            case KeyEvent.VK_Q:
                return KeyPressedEvent.KEY_Q;
            case KeyEvent.VK_R:
                return KeyPressedEvent.KEY_R;
            case KeyEvent.VK_S:
                return KeyPressedEvent.KEY_S;
            case KeyEvent.VK_T:
                return KeyPressedEvent.KEY_T;
            case KeyEvent.VK_U:
                return KeyPressedEvent.KEY_U;
            case KeyEvent.VK_V:
                return KeyPressedEvent.KEY_V;
            case KeyEvent.VK_W:
                return KeyPressedEvent.KEY_W;
            case KeyEvent.VK_X:
                return KeyPressedEvent.KEY_X;
            case KeyEvent.VK_Y:
                return KeyPressedEvent.KEY_Y;
            case KeyEvent.VK_Z:
                return KeyPressedEvent.KEY_Z;


            //SYSTEM KEYS
            case KeyEvent.VK_ESCAPE:
                return KeyPressedEvent.KEY_ESCAPE;
            case KeyEvent.VK_ENTER:
                return KeyPressedEvent.KEY_ENTER;
            case KeyEvent.VK_SPACE:
                return KeyPressedEvent.KEY_SPACE;
            case KeyEvent.VK_CONTROL:
                return KeyPressedEvent.KEY_CONTROL;
            case KeyEvent.VK_DELETE:
                return KeyPressedEvent.KEY_DELETE;
            case KeyEvent.VK_SHIFT:
                return KeyPressedEvent.KEY_SHIFT;
            case KeyEvent.VK_CAPS_LOCK:
                return KeyPressedEvent.KEY_CAPS_LOCK;
            case KeyEvent.VK_UP:
                return KeyPressedEvent.KEY_UP;
            case KeyEvent.VK_DOWN:
                return KeyPressedEvent.KEY_DOWN;
            case KeyEvent.VK_LEFT:
                return KeyPressedEvent.KEY_LEFT;
            case KeyEvent.VK_RIGHT:
                return KeyPressedEvent.KEY_RIGHT;
            case KeyEvent.VK_F1:
                return KeyPressedEvent.KEY_F1;
            case KeyEvent.VK_F2:
                return KeyPressedEvent.KEY_F2;
            case KeyEvent.VK_F3:
                return KeyPressedEvent.KEY_F3;
            case KeyEvent.VK_F4:
                return KeyPressedEvent.KEY_F4;
            case KeyEvent.VK_F5:
                return KeyPressedEvent.KEY_F5;
            case KeyEvent.VK_F6:
                return KeyPressedEvent.KEY_F6;
            case KeyEvent.VK_F7:
                return KeyPressedEvent.KEY_F7;
            case KeyEvent.VK_F8:
                return KeyPressedEvent.KEY_F8;
            case KeyEvent.VK_F9:
                return KeyPressedEvent.KEY_F9;
            case KeyEvent.VK_F10:
                return KeyPressedEvent.KEY_F10;
            case KeyEvent.VK_F11:
                return KeyPressedEvent.KEY_F11;
            case KeyEvent.VK_F12:
                return KeyPressedEvent.KEY_F12;

            default:
                return -1;
        }
    }

}
