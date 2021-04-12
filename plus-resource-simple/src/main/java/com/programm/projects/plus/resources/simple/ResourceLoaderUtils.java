package com.programm.projects.plus.resources.simple;

import com.programm.projects.plus.core.resource.Resource;
import com.programm.projects.plus.resource.api.MapResource;
import com.programm.projects.plus.resource.api.NamedResource;
import com.programm.projects.plus.resource.api.ValueResource;
import com.programm.projects.plus.resources.simple.utils.XmlReadException;
import com.programm.projects.plus.resources.simple.utils.XmlReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

class ResourceLoaderUtils {

    public static Map<String, Resource> loadFile(String path){
        if(path.endsWith(".properties")){
            return loadPropertiesFile(path);
        }
        else if(path.endsWith(".xml")){
            NamedResource resource = loadXmlFile(path);
            return Collections.singletonMap(resource.getName(), resource.getResource());
        }

        int lastDot = Math.max(0, path.lastIndexOf('.'));
        throw new IllegalStateException("Unsupported file - type: [" + path.substring(lastDot) + "]");
    }

    public static Map<String, Resource> loadPropertiesFile(String path){
        InputStream inputStream = ResourceLoaderUtils.class.getResourceAsStream(path);
        return loadPropertiesFile(inputStream);
    }

    public static Map<String, Resource> loadPropertiesFile(InputStream inputStream){
        Properties properties = new Properties();

        try {
            properties.load(inputStream);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        Map<String, Resource> map = new HashMap<>();

        properties.forEach((key, val) -> {
            map.put((String)key, ValueResource.OfUndefined(val));
        });

        return map;
    }

    public static NamedResource loadXmlFile(String path){
        InputStream inputStream = ResourceLoaderUtils.class.getResourceAsStream(path);
        return loadXmlFile(inputStream);
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
            Resource childResource = loadResourceFromXmlNode(child);
            resMap.put(child.name, childResource);
        }

        return new MapResource(resMap);
    }

}
