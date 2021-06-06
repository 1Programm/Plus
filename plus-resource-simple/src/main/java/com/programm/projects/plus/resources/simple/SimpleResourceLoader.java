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

    //HAS TO BE SET TO TRUE INITIALLY, OR THE ENGINE INITIALIZATION CANNOT COMPLETE!
    private boolean enabled = true;

    @Override
    public boolean setEnabled(boolean enabled) {
        this.enabled = enabled;
        return true;
    }

    @Override
    public void loadStaticResources() {
        log.debug("Loading static resources...");

        Map<String, Resource> engineDefProps = ResourceLoaderUtils.loadFile("/engine-default.properties");
        if(engineDefProps != null){
            log.debug("Found resource: [engine-default.properties].");
            insertStaticResource(engineDefProps);
        }

        Map<String, Resource> gameDefProps = ResourceLoaderUtils.loadFile("/game-default.properties");
        if(gameDefProps != null){
            log.debug("Found resource: [game-default.properties].");
            insertStaticResource(gameDefProps);
        }

        //Overriding default engine values
        Map<String, Resource> engineXml = ResourceLoaderUtils.loadFile("/engine.xml");
        Map<String, Resource> engineProps = ResourceLoaderUtils.loadFile("/engine.properties");

        if(engineXml != null){
            log.debug("Found resource: [engine.xml].");
            insertStaticResource(engineXml);
        }

        if(engineProps != null){
            log.debug("Found resource: [engine.properties].");
            insertStaticResource(engineProps);
        }

        //Overriding default engine values
        Map<String, Resource> gameXml = ResourceLoaderUtils.loadFile("/game.xml");
        Map<String, Resource> gameProps = ResourceLoaderUtils.loadFile("/game.properties");

        if(gameXml != null){
            log.debug("Found resource: [game.xml].");
            insertStaticResource(gameXml);
        }

        if(gameProps != null){
            log.debug("Found resource: [game.properties].");
            insertStaticResource(gameProps);
        }
    }

    private void insertStaticResource(Map<String, Resource> resourceMap){
        log.trace("Properties:");
        printResource(resourceMap, null);

        ResourceLoaderUtils.insertResource(staticResources, resourceMap);
    }

    private void printResource(Map<String, Resource> map, String path){
        if(path == null){
            path = "";
        }
        else {
            path += ".";
        }

        for(String key : map.keySet()){

            Resource resource = map.get(key);
            if(!(resource instanceof MapResource mapResource)){
                String val = resource.asString("undefined");
                log.trace(" | {} = {}", path + key, val);
            }
            else {
                printResource(mapResource.getResourceMap(), path + key);
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
        if(!enabled){
            return new NullResource("Resource Loader is disabled and could not return the resource: [" + name + "]!");
        }


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
            if(!parentResource.isNull()){
                resource = parentResource.get(valueName);
            }
        }

        if(resource != null){
            return resource;
        }

        //return empty resource so no null pointer is thrown
        return new NullResource("[" + name + "]: This resource is an EmptyResource and should only be used with defaults. A resource can be checked if it represents an empty resource by the isEmptyResource() method.");
    }
}
