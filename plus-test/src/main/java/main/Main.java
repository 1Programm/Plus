package main;

import com.programm.projects.plus.engine.api.IEngine;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception{
        IEngine engine = new SimpleEngine();
        engine.startup();

        Thread.sleep(5000);

        engine.shutdown();
    }

}
