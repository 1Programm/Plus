package com.programm.projects.plus.resources.simple;

import com.programm.projects.plus.resource.api.MapResource;
import com.programm.projects.plus.resource.api.NullResource;
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
        log.debug("Loading static resources...");

        Map<String, Resource> engineDefProps = ResourceLoaderUtils.loadFile("/engine-default.properties");
        if(engineDefProps != null){
            log.debug("Found engine-default.properties - loading default engine properties.");
            insertStaticResource(engineDefProps);
        }

        Map<String, Resource> gameDefProps = ResourceLoaderUtils.loadFile("/game-default.properties");
        if(gameDefProps != null){
            log.debug("Found game-default.properties - loading default game properties.");
            insertStaticResource(gameDefProps);
        }

        //Overriding default engine values
        Map<String, Resource> engineXml = ResourceLoaderUtils.loadFile("/engine.xml");
        Map<String, Resource> engineProps = ResourceLoaderUtils.loadFile("/engine.properties");

        if(engineXml != null){
            log.debug("Found engine.xml - loading engine properties.");
            insertStaticResource(engineXml);
        }

        if(engineProps != null){
            log.debug("Found engine.properties - loading engine properties.");
            insertStaticResource(engineProps);
        }

        //Overriding default engine values
        Map<String, Resource> gameXml = ResourceLoaderUtils.loadFile("/game.xml");
        Map<String, Resource> gameProps = ResourceLoaderUtils.loadFile("/game.properties");

        if(gameXml != null){
            log.debug("Found game.xml - loading game properties.");
            insertStaticResource(gameXml);
        }

        if(gameProps != null){
            log.debug("Found game.properties - loading engine properties.");
            insertStaticResource(gameProps);
        }
    }

    private void insertStaticResource(Map<String, Resource> resourceMap){
        log.trace("Properties:");
        printResource(resourceMap, null);

        ResourceLoaderUtils.insertResource(staticResources, resourceMap);
    }

    private void printResource(Map<String, Resource> map, String path){
        for(String key : map.keySet()){
            if(path == null){
                path = key;
            }
            else {
                path += "." + key;
            }

            Resource resource = map.get(key);
            if(!(resource instanceof MapResource mapResource)){
                String val = resource.asString("undefined");
                log.trace(" | {} = {}", path, val);
            }
            else {
                printResource(mapResource.getResourceMap(), path);
            }
        }
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

        Resource resource = null;

        int lastDot = name.lastIndexOf('.');
        if(lastDot != -1){
            String parentPath = name.substring(0, lastDot);
            String valueName = name.substring(lastDot + 1);

            Resource parentResource = getResource(parentPath);
            resource = parentResource.get(valueName);
        }

        if(resource != null){
            return resource;
        }

        //return empty resource so no null pointer is thrown
        return new NullResource();
    }
}
