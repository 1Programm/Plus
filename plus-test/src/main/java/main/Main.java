package main;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.engine.api.IEngine;
import com.programm.projects.plus.engine.api.events.EnginePhaseEvent;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import com.programm.projects.plus.renderer.api.components.ColorMaterial;
import com.programm.projects.plus.renderer.api.components.Model;
import lombok.extern.slf4j.Slf4j;
import main.components.KeyboardController;

import java.awt.*;

@Slf4j
public class Main {

    private static void onPhaseChange(EnginePhaseEvent e){
        log.info("Before: {}, Phase: {}", e.getBefore(), e.getPhase());
    }

    public static void main(String[] args) throws Exception{
        IEngine engine = new SimpleEngine();
        engine.events().listenFor(EnginePhaseEvent.class, Main::onPhaseChange);

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
