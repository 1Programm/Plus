package com.programm.projects.plus.resource.api;

import com.programm.projects.plus.core.lifecycle.ILifecycle;
import com.programm.projects.plus.core.resource.IResources;
import com.programm.projects.plus.core.resource.Resource;

public interface IResourceManager extends ILifecycle, IResources {

    void loadStaticResources();

    /**
     * Method to get a specific resource.
     * A resource can range from a simple value to a scene resource or sound file, mesh file, usw...
     * @param name The name of the resource
     * @return A resource if the <code>name</code> matches. Or {@link NullResource} if no resource was found.
     */
    @Override
    Resource getResource(String name);
}
