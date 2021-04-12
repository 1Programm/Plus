package com.programm.projects.plus.resources.simple;

import com.programm.projects.plus.resource.api.EmptyResource;
import com.programm.projects.plus.resource.api.IResourceManager;
import com.programm.projects.plus.core.resource.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SimpleResourceLoader implements IResourceManager {

    private final Map<String, Resource> staticResources = new HashMap<>();

    @Override
    public void loadStaticResources() {
        loadFile("/engine-default.properties");
        loadFile("/game-default.properties");

        //Overriding default values
        loadFile("/engine.properties");
        loadFile("/game.properties");
    }

    private void loadFile(String path){
        Map<String, Resource> resourceMap = ResourceLoaderUtils.loadFile(path);
        staticResources.putAll(resourceMap);
    }

    private void loadPropertyFile(String path){
        Map<String, Resource> properties = ResourceLoaderUtils.loadPropertiesFile(path);
        staticResources.putAll(properties);
    }

    @Override
    public void startup() {
        log.info("[Startup] - Simple Resource Loader");

    }

    @Override
    public void shutdown() {
        log.info("[Shutdown] - Simple Resource Loader");
        //Cleanup
    }

    @Override
    public Resource getResource(String name) {
        Resource staticRes = staticResources.get(name);

        if(staticRes != null){
            return staticRes;
        }

        int lastDot = name.lastIndexOf('.');
        if(lastDot != -1){
            String parentPath = name.substring(0, lastDot);
            String valueName = name.substring(lastDot + 1);

            Resource parentResource = getResource(parentPath);
            return parentResource.get(valueName);
        }


        //return empty resource so no null pointer is thrown
        return new EmptyResource();
    }
}
