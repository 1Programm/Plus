package com.programm.projects.plus.resources.simple.utils;

import lombok.Getter;

import java.io.IOException;

public class XmlParseException extends IOException {

    @Getter
    private final int index;

    public XmlParseException(int index) {
        this.index = index;
    }

    public XmlParseException(String message, int index) {
        super(message);
        this.index = index;
    }

    public XmlParseException(String message, Throwable cause, int index) {
        super(message, cause);
        this.index = index;
    }

    public XmlParseException(Throwable cause, int index) {
        super(cause);
        this.index = index;
    }
}
