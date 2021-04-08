package com.programm.projects.plus.resource.api;

import com.programm.projects.plus.core.lifecycle.ILifecycle;

public interface IResourceManager extends ILifecycle {

    void loadStaticResources();

    Resource getResource(String name);

}
