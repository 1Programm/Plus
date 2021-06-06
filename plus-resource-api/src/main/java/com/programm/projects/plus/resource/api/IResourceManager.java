package com.programm.projects.plus.resource.api;

import com.programm.projects.plus.core.ISubsystem;
import com.programm.projects.plus.core.lifecycle.ILifecycle;
import com.programm.projects.plus.core.resource.IResources;
import com.programm.projects.plus.core.resource.Resource;

public interface IResourceManager extends ISubsystem, IResources {

    /**
     * Method to add a resource path which should be loaded on initialization.
     * All static resources will be loaded all together once {@link IResourceManager#loadStaticResources()} is called.
     * After that this method will load the resource immediately.
     * @param path of the resource
     */
    void addStaticResource(String path);

    /**
     * Method to load all defined static resources.
     */
    void loadStaticResources();

    /**
     * Method to get a specific resource.
     * A resource can range from a simple value to a scene resource or sound file, mesh file, usw...
     * To load files you have to add a "f:" before the file name.
     * This will tell the resource manager to load the file from disc and not to search through preloaded resources.
     * @param name The name of the resource
     * @return A resource if the <code>name</code> matches. Or {@link NullResource} if no resource was found.
     */
    @Override
    Resource getResource(String name);
}
