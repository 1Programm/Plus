package com.programm.plus.render.api;

public interface Renderer {

    GameWindow initWindow(String title, int width, int height);

    Pencil initPencil();

    void preRender();

    void postRender();

    void cleanUp();

}
