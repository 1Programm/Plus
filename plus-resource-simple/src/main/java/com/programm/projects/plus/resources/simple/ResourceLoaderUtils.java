package com.programm.projects.plus.resources.simple;

import com.programm.projects.plus.core.resource.Resource;
import com.programm.projects.plus.resource.api.MapResource;
import com.programm.projects.plus.resource.api.NamedResource;
import com.programm.projects.plus.resource.api.ValueResource;
import com.programm.projects.plus.resources.simple.utils.XmlReadException;
import com.programm.projects.plus.resources.simple.utils.XmlReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

class ResourceLoaderUtils {

    public static void insertResource(Map<String, Resource> map, Map<String, Resource> nMap){
        for(String key : nMap.keySet()){
            Resource resource = map.get(key);

            if(!(resource instanceof MapResource mapResource)){
                map.put(key, nMap.get(key));
            }
            else {
                Resource nMapRes = nMap.get(key);
                if(!(nMapRes instanceof MapResource mapResource2)){
                    throw new IllegalStateException("INVALID STATE"); // IDK
                }
                else {
                    insertResource(mapResource.getResourceMap(), mapResource2.getResourceMap());
                }
            }
        }
    }

    public static Map<String, Resource> loadFile(String path){
        if(path.endsWith(".properties")){
            return loadPropertiesFile(path);
        }
        else if(path.endsWith(".xml")){
            return loadXmlFile(path);
        }

        int lastDot = Math.max(0, path.lastIndexOf('.'));
        throw new IllegalStateException("Unsupported file - type: [" + path.substring(lastDot) + "]");
    }

    public static Map<String, Resource> loadPropertiesFile(String path){
        InputStream inputStream = ResourceLoaderUtils.class.getResourceAsStream(path);
        return loadPropertiesFile(inputStream);
    }

    public static Map<String, Resource> loadPropertiesFile(InputStream inputStream){
        if(inputStream == null) return null;

        Properties properties = new Properties();

        try {
            properties.load(inputStream);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        Map<String, Resource> map = new HashMap<>();

        for(String key : properties.stringPropertyNames()){
            insertResource(map, key, properties.getProperty(key));
        }

        return map;
    }

    private static void insertResource(Map<String, Resource> map, String key, String resource){
        String[] split = key.split("\\.", 2);

        if(split.length == 1){
            map.put(split[0], ValueResource.OfUndefined(resource));
        }
        else {
            Resource res = map.get(split[0]);

            if(!(res instanceof MapResource mRes)){
                Map<String, Resource> nMap = new HashMap<>();
                insertResource(nMap, split[1], resource);
                res = new MapResource(nMap);
                map.put(split[0], res);
            }
            else {
                insertResource(mRes.getResourceMap(), split[1], resource);
            }
        }
    }

    public static Map<String, Resource> loadXmlFile(String path){
        InputStream inputStream = ResourceLoaderUtils.class.getResourceAsStream(path);

        if(inputStream == null) return null;

        NamedResource res = loadXmlFile(inputStream);
        return Collections.singletonMap(res.getName(), res.getResource());
    }

    public static NamedResource loadXmlFile(InputStream inputStream){
        try {
            XmlReader.Node root = XmlReader.read(inputStream);
            return loadMapResourceFromXmlNode(root);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load xml file!", e);
        }
    }

    private static NamedResource loadMapResourceFromXmlNode(XmlReader.Node node) throws XmlReadException {
        Resource resource = loadResourceFromXmlNode(node);
        return new NamedResource(node.name, resource);
    }

    private static Resource loadResourceFromXmlNode(XmlReader.Node node){
        if(node.children == null){
            return ValueResource.OfUndefined(node.name);
        }

        Map<String, Resource> resMap = new HashMap<>();

        for(XmlReader.Node child : node.children){
            if(child.children != null && child.children.size() == 1 && child.children.get(0).children == null){
                ValueResource vRes = ValueResource.OfUndefined(child.children.get(0).name);
                resMap.put(child.name, vRes);
            }
            else {
                Resource childResource = loadResourceFromXmlNode(child);
                resMap.put(child.name, childResource);
            }
        }

        return new MapResource(resMap);
    }

}
