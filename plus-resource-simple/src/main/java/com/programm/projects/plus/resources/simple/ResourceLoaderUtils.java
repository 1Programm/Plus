package com.programm.projects.plus.resources.simple;

import com.programm.projects.plus.resource.api.MapResource;
import com.programm.projects.plus.resource.api.Resource;
import com.programm.projects.plus.resource.api.ValueResource;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

class ResourceLoaderUtils {

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

}
