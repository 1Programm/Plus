package com.programm.projects.plus.engine.api;

import com.programm.projects.plus.resource.api.IResourceManager;
import com.programm.projects.plus.core.resource.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class EngineStaticLogger {

    private static final String DEFAULTS_DEFAULT_START_MESSAGE = "-[ missing startup message ]-";
    private static final String DEFAULTS_DEFAULT_STOP_MESSAGE = "-[ missing shutdown message ]-";

    public static void logStartup(IResourceManager resourceManager){
        Resource resource = resourceManager.getResource("plus.engine.start-message");

        if(resource != null){
            String message = resource.asString(DEFAULTS_DEFAULT_START_MESSAGE);
            message = formatMessage(message);
            log.info("\n" + message + "\n");
        }
    }

    public static void logShutdown(IResourceManager resourceManager){
        Resource resource = resourceManager.getResource("plus.engine.stop-message");

        if(resource != null){
            String message = resource.asString(DEFAULTS_DEFAULT_STOP_MESSAGE);
            message = formatMessage(message);
            log.info("\n" + message);
        }
    }

    private static String formatMessage(String message){
        message = "===== " + message + " =====";

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<message.length();i++){
            sb.append("=");
        }

        return sb + "\n" + message + "\n" + sb;
    }

}
