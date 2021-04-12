package com.programm.projects.plus.resource.api;

import com.programm.projects.plus.core.resource.Resource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class NamedResource implements DefaultValuesResource{

    private final String name;
    private final Resource resource;

    @Override
    public Resource get(String name) {
        return resource;
    }
}
