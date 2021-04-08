package com.programm.projects.plus.resources.simple;

import com.programm.projects.plus.resource.api.IResourceManager;
import com.programm.projects.plus.resource.api.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SimpleResourceLoader implements IResourceManager {

    private final Map<String, Resource> staticResources = new HashMap<>();

    @Override
    public void loadStaticResources() {
        Map<String, Resource> defaultEngineProperties = ResourceLoaderUtils.loadPropertiesFile("/engine-default.properties");
        staticResources.putAll(defaultEngineProperties);

        //Overriding default values
        Map<String, Resource> engineProperties = ResourceLoaderUtils.loadPropertiesFile("/engine.properties");
        staticResources.putAll(engineProperties);
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

        return null;
    }
}
