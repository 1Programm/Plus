package com.programm.projects.plus.engine.simple.logging;

import ch.qos.logback.core.PropertyDefinerBase;
import com.programm.projects.plus.resources.simple.utils.XmlReader;
import lombok.Setter;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * Regenerates all files which are used in logback and are not a Properties file into one and providing the new path
 */
@Setter
public class LogbackExternalVariables extends PropertyDefinerBase {

    private static final String TYPE_GENERATED = "p-gen";
    private static final String TYPE_XML = "xml";

    private String name;
    private String type;

    @Override
    public String getPropertyValue() {
        String fileName = name + "." + type;
        String generatedFolder = "p-gen";
        String nFileName = generatedFolder + "/" + fileName + "." + TYPE_GENERATED;

        try {
            InputStream inputStream = LogbackExternalVariables.class.getResourceAsStream("/" + fileName);

            URL url = LogbackExternalVariables.class.getResource("/");
            if(url == null){
                //Should not be null as "/" is always defined
                throw new IllegalStateException("INVALID STATE!");
            }

            File rootFolder = new File(url.toURI());
            File genFolder = new File(rootFolder, generatedFolder);
            genFolder.mkdir();

            if(inputStream != null) {
                if(type.equals(TYPE_XML)){
                    XmlReader.Node root = XmlReader.read(inputStream);
                    Properties properties = XmlReader.parseNodeToProperties(root);

                    File nFile = new File(rootFolder, nFileName);

                    try (OutputStream os = new FileOutputStream(nFile)) {
                        properties.store(os, null);
                    }

                    return nFileName;
                }
            }
            else {
                File nFile = new File(rootFolder, nFileName);
                nFile.createNewFile();

                return nFileName;
            }
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException("Exception getting external vars for logback.", e);
        }

        return fileName;
    }
}
