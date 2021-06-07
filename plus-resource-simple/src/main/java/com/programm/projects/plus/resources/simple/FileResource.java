package com.programm.projects.plus.resources.simple;

import com.programm.projects.plus.core.resource.Resource;
import com.programm.projects.plus.resource.api.DefaultValuesResource;
import lombok.RequiredArgsConstructor;

import java.io.File;

@RequiredArgsConstructor
public class FileResource implements DefaultValuesResource {

    private final File file;

    @Override
    public boolean isFile() {
        return true;
    }

    @Override
    public File asFile() {
        return file;
    }

    @Override
    public Resource get(String name) {
        return null;
    }

    @Override
    public String toString() {
        return "File: [path:" + file.getAbsolutePath() + "]";
    }
}
