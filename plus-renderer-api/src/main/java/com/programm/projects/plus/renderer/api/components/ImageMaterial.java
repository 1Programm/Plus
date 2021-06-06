package com.programm.projects.plus.renderer.api.components;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ImageMaterial implements Material {

    private final String resourcePath;
}

