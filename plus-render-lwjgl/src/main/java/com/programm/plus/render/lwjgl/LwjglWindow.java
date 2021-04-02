package com.programm.plus.render.lwjgl;

import static org.lwjgl.glfw.GLFW.*;

import com.programm.plus.core.Controlls;
import com.programm.plus.core.GameWindow;
import org.lwjgl.opengl.GL;

public class LwjglWindow extends GameWindow {

    private boolean initialized;

    private long window;

    private String title;
    private int width, height;

    public LwjglWindow(long window, String title, int width, int height){
        this.window = window;
        this.title = title;
        this.width = width;
        this.height = height;
    }

    @Override
    protected Controlls initControlls() {
        return new Controlls();
    }

    @Override
    public void setTitle(String title) {
        this.title = title;

        glfwSetWindowTitle(window, title);
    }

    @Override
    public void setWidth(int width) {
        this.width = width;

        glfwSetWindowSize(window, width, getHeight());
    }

    @Override
    public void setHeight(int height) {
        this.height = height;

        glfwSetWindowSize(window, getWidth(), height);
    }

    @Override
    public void setVisible(boolean visible) {
        if(visible){
            glfwShowWindow(window);

            if (!initialized) {
                GL.createCapabilities();
                initialized = true;
            }
        }
        else{
            glfwHideWindow(window);
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }
}
