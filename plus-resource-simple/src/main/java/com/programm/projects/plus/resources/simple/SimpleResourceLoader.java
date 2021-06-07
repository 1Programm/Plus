package com.programm.projects.plus.resources.simple;

import com.programm.projects.plus.resource.api.MapResource;
import com.programm.projects.plus.resource.api.NullResource;
import com.programm.projects.plus.resource.api.IResourceManager;
import com.programm.projects.plus.core.resource.Resource;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SimpleResourceLoader implements IResourceManager {

    private final List<String> staticResourcePaths = new ArrayList<>();
    private boolean loadedStatics = false;

    private final Map<String, Resource> staticResources = new HashMap<>();
    private final Map<String, Resource> dynamicResources = new HashMap<>();

    //HAS TO BE SET TO TRUE INITIALLY, OR THE ENGINE INITIALIZATION CANNOT COMPLETE!
    private boolean enabled = true;

    @Override
    public boolean setEnabled(boolean enabled) {
        this.enabled = enabled;
        return true;
    }

    @Override
    public void addStaticResource(String path) {
        staticResourcePaths.add(path);

        if(loadedStatics){
            loadResource(path);
        }
    }

    @Override
    public void loadStaticResources() {
        log.debug("# Loading static resources...");

        for(String path : staticResourcePaths){
            loadResource(path);
        }

        loadedStatics = true;
    }

    private void loadResource(String path){
        Map<String, Resource> props = ResourceLoaderUtils.loadFile(path);
        String pre = log.isTraceEnabled() ? "| # " : "| ";

        if(props != null){
            log.debug(pre + "Static resource: [" + path + "].");
            insertStaticResource(props);
        }
        else {
            log.debug(pre + "Couldn't find [" + path + "].");
        }
    }

    private void insertStaticResource(Map<String, Resource> resourceMap){
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
                log.trace("| | {} = {}", path + key, val);
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

        Resource resource;

        //FILE
        if(name.startsWith("f:")){
            resource = getFileResource(name);
        }
        //STATIC RESOURCE
        else {
            resource = getStaticResource(name, true);
        }

        if(resource != null){
            return resource;
        }

        log.trace("Could not find resource [{}]!", name);

        //return empty resource so no null pointer is thrown
        return new NullResource("[" + name + "]: This resource is an EmptyResource and should only be used with defaults. A resource can be checked if it represents an empty resource by the isEmptyResource() method.");
    }

    private Resource getStaticResource(String name, boolean print){
        if(print){
            log.trace("Getting Resource [{}] ...", name);
        }

        //Try full path
        Resource staticRes = staticResources.get(name);

        if(staticRes != null){
            return staticRes;
        }


        //Try with "." separated
        Resource resource = null;

        int lastDot = name.lastIndexOf('.');
        if(lastDot != -1){
            String parentPath = name.substring(0, lastDot);
            String valueName = name.substring(lastDot + 1);

            Resource parentResource = getStaticResource(parentPath, false);
            if(!parentResource.isNull()){
                resource = parentResource.get(valueName);
            }
        }

        return resource;
    }

    private Resource getFileResource(String name){
        Resource resource = dynamicResources.get(name);

        if(resource != null){
            if(resource.isFile()){
                return resource;
            }
            else {
                log.warn("Resource [{}] is not a file and collides with filepath: [{}]", resource, name);
                return resource;
            }
        }
        else {
            // name = "f:..."
            String filePath = name.substring(2);
            URL url = SimpleResourceLoader.class.getResource(filePath);

            if(url != null){
                try {
                    File file = new File(url.toURI());

                    if(!file.isFile()){
                        return new NullResource("Could not find file: [" + filePath + "]!");
                    }

                    FileResource fileResource = new FileResource(file);
                    dynamicResources.put(name, fileResource);
                    return fileResource;
                }
                catch (URISyntaxException e) {
                    log.error("URI SyntaxException: Could not parse url to uri: [" + url + "]!");
                }
            }

            //TODO: CONFIG A WORKSPACE PATH WHERE USER CAN STORE RESOURCES AND CHECK THAT WORKSPACE FOR RES HERE
            return new NullResource("Could not find file: [" + filePath + "]!");
        }
    }
}
