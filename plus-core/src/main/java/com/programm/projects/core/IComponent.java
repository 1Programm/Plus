package com.programm.projects.core;

public interface IComponent extends IInitializable{

    @Override
    default void init(IEngineContext context) { }

}
