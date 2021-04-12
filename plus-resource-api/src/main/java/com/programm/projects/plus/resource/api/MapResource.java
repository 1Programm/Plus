package com.programm.projects.plus.resource.api;


import com.programm.projects.plus.core.resource.Resource;
import lombok.Getter;

import java.util.Map;

public class MapResource implements DefaultValuesResource{

    @Getter
    private final Map<String, Resource> resourceMap;

    public MapResource(Map<String, Resource> resourceMap) {
        this.resourceMap = resourceMap;
    }

    @Override
    public Resource get(String name) {
        return resourceMap.get(name);
    }
}
