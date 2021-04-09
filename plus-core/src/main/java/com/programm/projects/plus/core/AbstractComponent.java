package com.programm.projects.plus.core;

import com.programm.projects.plus.core.components.Transform;

public class AbstractComponent implements IComponent {

    private GameObject parent;

    void setParent(GameObject parent){
        this.parent = parent;
    }

    protected Transform transform(){
        return parent.getTransform();
    }

}
