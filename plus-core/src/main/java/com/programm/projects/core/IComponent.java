package com.programm.projects.core;

public interface IComponent extends IUpdatable, IInitializable{

    default boolean canHaveMultiple(){
        return false;
    }

}
