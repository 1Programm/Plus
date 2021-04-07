package com.programm.projects.plus.core;

public interface IGameContext {

    GameObject getObject();

    IGameContext getParentContext();




    IGameContext createFromNewParent(GameObject object);

    void revert();

}
