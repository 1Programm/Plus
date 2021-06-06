package com.programm.projects.plus.resource.api;


import com.programm.projects.plus.core.resource.Resource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class MapResource implements DefaultValuesResource{

    private final Map<String, Resource> resourceMap;

    @Override
    public Resource get(String name) {
        return resourceMap.get(name);
    }
}
