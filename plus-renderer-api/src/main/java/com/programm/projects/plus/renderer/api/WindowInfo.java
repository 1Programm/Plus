package com.programm.projects.plus.renderer.api;

import lombok.Getter;

@Getter
public class WindowInfo {

    private final String title;
    private final int width, height;

    public WindowInfo(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }
}
