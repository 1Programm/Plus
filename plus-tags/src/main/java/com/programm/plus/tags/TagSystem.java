package com.programm.plus.tags;

import com.programm.plus.goh.api.GameObjectInitializer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TagSystem {

    public static final String MODE_REQUIRED_CHECK_WARN = "check_warn_mode";
    public static final String MODE_REQUIRED_CHECK_ERROR = "check_error_mode";
    public static final String MODE_REQUIRED_FORCE_CREATE = "force_create_mode";
    public static final String MODE_REQUIRED_WARN_FORCE_CREATE = "warn_force_create_mode";

    private GameObjectTagInitializer tagInitializer = new GameObjectTagInitializer();

    public TagSystem(){
        this.tagInitializer.tagInitMode = MODE_REQUIRED_CHECK_WARN;
    }

    public TagSystem setTagInitMode(String tagInitMode){
        this.tagInitializer.tagInitMode = tagInitMode;

        return this;
    }

    public GameObjectInitializer getObjectInitializer(){
        return tagInitializer;
    }

}
