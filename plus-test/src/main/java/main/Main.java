package main;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.engine.api.IEngine;
import com.programm.projects.plus.engine.api.events.EnginePhaseEvent;
import com.programm.projects.plus.engine.simple.SimpleEngine;
import com.programm.projects.plus.goh.api.IObjectConsumer;
import com.programm.projects.plus.renderer.api.components.ColorMaterial;
import com.programm.projects.plus.renderer.api.components.Model;
import com.programm.projects.plus.renderer.api.events.KeyPressedEvent;
import lombok.extern.slf4j.Slf4j;
import main.components.KeyboardController;

import java.awt.*;

@Slf4j
public class Main {

    private static void onPhaseChange(EnginePhaseEvent e){
        log.info("Before: {}, Phase: {}", e.getBefore(), e.getPhase());
    }

    private static void onKeyPressed(KeyPressedEvent e){
        log.info("Key: {}.", e.getKeyCode());
    }

    private static void sceneInit(IObjectConsumer objects){
        objects.add(GameObject.create()
                .setPosition(100, 100)
                .setScale(2, 2)
                .add(new KeyboardController(3))
                .add(new ColorMaterial(Color.BLACK, Color.RED))
                .add(Model.Rectangle(32, 32))
                .build()
        );
    }

    public static void main(String[] args) throws Exception{
        IEngine engine = new SimpleEngine();

        engine.setSceneInitializer(Main::sceneInit);
        engine.events().listenFor(EnginePhaseEvent.class, Main::onPhaseChange);
        engine.events().listenFor(KeyPressedEvent.class, Main::onKeyPressed);

        engine.startup();
    }

}
