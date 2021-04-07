package main;

import com.programm.projects.core.*;
import com.programm.projects.plus.renderer.api.components.ColorMaterial;
import com.programm.projects.plus.renderer.api.components.Model;
import com.programm.projects.plus.engine.api.IEngine;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import lombok.extern.slf4j.Slf4j;
import main.components.KeyboardController;

import java.awt.*;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception{
        IEngine engine = new SimpleEngine();

        IGameObjectHandler goh = engine.getGOH();
        goh.addObject(GameObject.create()
                .setPosition(100, 100)
                .setScale(2, 2)
                .add(new KeyboardController(5))
                .add(new ColorMaterial(Color.BLACK, Color.RED))
                .add(Model.Rectangle(32, 32))
                .build()
        );

        engine.startup();
    }

}
