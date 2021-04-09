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
        loadPropertyFile("/engine-default.properties");
        loadPropertyFile("/game-default.properties");

        //Overriding default values
        loadPropertyFile("/engine.properties");
        loadPropertyFile("/game.properties");
    }

    private void loadPropertyFile(String resource){
        Map<String, Resource> properties = ResourceLoaderUtils.loadPropertiesFile(resource);
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


        //return empty resource so no null pointer is thrown
        return new EmptyResource();
    }
}
