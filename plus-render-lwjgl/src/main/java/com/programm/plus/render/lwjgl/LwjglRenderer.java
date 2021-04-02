package com.programm.plus.render.lwjgl;

import com.programm.plus.core.GameWindow;
import com.programm.plus.render.api.Pencil;
import com.programm.plus.render.api.Renderer;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

@Slf4j
public class LwjglRenderer implements Renderer {

    private long window;

    @Override
    public GameWindow initWindow(String title, int width, int height) {
        log.info("Initializing Lwjgl Renderer...");

        window = initLwjgl(title, width, height);

        return new LwjglWindow(window, title, width, height);
    }

    @Override
    public Pencil initPencil() {
        return new LwjglPencil(window);
    }

    private long initLwjgl(String title, int width, int height) {
        log.debug("Set error callback to 'System.err'");
        GLFWErrorCallback.createPrint(System.err).set();

        log.debug("Init glfw...");
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        log.debug("Creating new Window with Dimensions: ({}, {})", width, height);
        long window = glfwCreateWindow(width, height, title, NULL, NULL);

        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        if(vidmode == null)
            throw new RuntimeException("Failed to get Vidmode");

        int xPos = (vidmode.width() - width) / 2;
        int yPos = (vidmode.height() - height) / 2;

        log.debug("Set Position of window to: ({}, {})", xPos, yPos);
        glfwSetWindowPos(window, xPos, yPos);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        return window;
    }

    @Override
    public void preRender() {

    }

    @Override
    public void postRender() {
        glfwSwapBuffers(window);

        glfwPollEvents();
    }

    @Override
    public void cleanUp() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
