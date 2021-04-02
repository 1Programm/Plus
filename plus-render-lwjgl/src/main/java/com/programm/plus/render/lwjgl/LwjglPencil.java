package com.programm.plus.render.lwjgl;

import com.programm.plus.core.GameWindow;
import com.programm.plus.render.api.Pencil;

import java.awt.Color;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class LwjglPencil implements Pencil {

    private long window;

    private boolean colorChanged;

    private float r, g, b;
    private String paintMode;

    public LwjglPencil(long window){
        this.window = window;
        this.colorChanged = true;
    }

    @Override
    public void setColor(Color color) {
        this.r = (color.getRed() / 255.0f);
        this.g = (color.getGreen() / 255.0f);
        this.b = (color.getBlue() / 255.0f);

        glColor3f(r, g, b);

        this.colorChanged = true;
    }

    @Override
    public void setPaintMode(String paintMode) {
        this.paintMode = paintMode;
    }

    @Override
    public void clearWindow(GameWindow gameWindow) {
        if(colorChanged) {
            glClearColor(r, g, b, 0.0f);
            colorChanged = false;
        }

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void drawRect(float x, float y, float width, float height) {
        float w2 = width/2;
        float h2 = height/2;

        float x2 = x - w2;
        float y2 = y - h2;

        glBegin(GL_QUADS);
        glVertex2f(x2 + w2, y2 + h2);
        glVertex2f(x2 + w2, y2);
        glVertex2f(x2, y2);
        glVertex2f(x2, y2 + h2);
        glEnd();
    }

    @Override
    public void drawCircle(float x, float y, float radius) {
        //TODO
    }
}
