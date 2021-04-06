package com.programm.projects.plus.renderer.api;

public interface IMouse extends EventConstants.Mouse{

    int getX();
    int getY();
    int getScroll();

    int getDragStartX();
    int getDragStartY();

    boolean isButtonPressed(int button);

}
