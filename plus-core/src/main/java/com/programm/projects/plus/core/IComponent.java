package com.programm.projects.plus.core;

public interface IComponent extends IInitializable{

    @Override
    default void init(IEngineContext context) { }

}
