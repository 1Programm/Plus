package main;

import com.programm.projects.plus.components.basic.KeyboardController;
import com.programm.projects.plus.components.basic.VelocityRotator;
import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.components.Camera;
import com.programm.projects.plus.engine.api.IEngine;
import com.programm.projects.plus.engine.api.Scene;
import com.programm.projects.plus.engine.simple.SimpleEngine;
import com.programm.projects.plus.goh.api.IObjectConsumer;
import com.programm.projects.plus.renderer.api.IKeyboard;
import com.programm.projects.plus.renderer.api.components.ColorMaterial;
import com.programm.projects.plus.renderer.api.components.Model;
import com.programm.projects.plus.renderer.api.events.KeyReleasedEvent;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class Main extends Scene {

    public static void main(String[] args) {
        IEngine engine = new SimpleEngine();
        engine.setScene(new Main());

        engine.events().listenFor(KeyReleasedEvent.class, e -> {
            if(e.getKeyCode() == IKeyboard.KEY_ESCAPE){
                engine.shutdown();
            }
        });

        engine.startup();
    }

    @Override
    protected void initScene(IObjectConsumer objects) {
        objects.add(GameObject.create()
                .setPosition(0, 0)
                .add(new KeyboardController(100))
                .add(new Camera())
                .add(new ColorMaterial(Color.BLUE))
                .add(Model.Rectangle(32, 32))
                .add(new VelocityRotator(1))
                .build());

        objects.add(GameObject.create()
                .add(new ColorMaterial(Color.BLACK))
                .add(Model.Line(0, 0, 100, 10))
                .build());

        objects.add(GameObject.create()
                .setPosition(200, 200)
                .setScale(1, 1)
                .add(new ColorMaterial(Color.BLACK, Color.RED))
                .add(Model.Rectangle(100, 100))
                .build());

        objects.add(GameObject.create()
                .setPosition(200, 200)
                .setScale(3, 1)
                .add(new ColorMaterial(Color.BLACK))
                .add(Model.Circle(50))
                .build());
    }
}
