package com.programm.projects.plus.resources.simple.utils;

import java.io.IOException;

public class XmlReadException extends IOException {

    public XmlReadException() {
    }

    public XmlReadException(String message) {
        super(message);
    }

    public XmlReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlReadException(Throwable cause) {
        super(cause);
    }
}
