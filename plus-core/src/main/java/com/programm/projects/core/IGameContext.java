package com.programm.projects.core;

public interface IGameContext {

    GameObject getObject();

    IGameContext getParentContext();




    IGameContext createFromNewParent(GameObject object);

    void revert();

}
