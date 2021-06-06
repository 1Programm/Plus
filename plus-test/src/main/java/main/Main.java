package main;

import com.programm.projects.plus.collision.api.Collider;
import com.programm.projects.plus.components.basic.KeyboardController;
import com.programm.projects.plus.components.basic.events.ShutdownOnKeyPress;
import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.components.Camera;
import com.programm.projects.plus.engine.api.IEngine;
import com.programm.projects.plus.engine.api.Scene;
import com.programm.projects.plus.engine.simple.SimpleEngine;
import com.programm.projects.plus.goh.api.IObjectConsumer;
import com.programm.projects.plus.renderer.api.components.ColorMaterial;
import com.programm.projects.plus.renderer.api.components.ImageMaterial;
import com.programm.projects.plus.renderer.api.components.Model;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class Main extends Scene {

    public static void main(String[] args) throws Exception{
        IEngine engine = new SimpleEngine();
        engine.setScene(new Main());

        ShutdownOnKeyPress.Escape(engine);

        engine.startup();
    }

    @Override
    protected void initScene(IObjectConsumer objects) {
        objects.add(GameObject.create()
                .setPosition(130, 150)
                .add(new KeyboardController(100))
                .add(new Collider())
                .add(new Camera())
                .add(new ImageMaterial("f:/test.png"))
                .add(Model.Rectangle(32, 32))
                .build());

        objects.add(GameObject.create()
                .setPosition(-100, 140)
                .add(new ColorMaterial(Color.BLACK))
                .add(new Collider())
                .add(Model.Line(0, 0, 100, 160))
                .build());

        objects.add(GameObject.create()
                .setPosition(230, 200)
                .add(new ColorMaterial(Color.BLACK, Color.RED))
                .add(Model.Rectangle(100, 100))
                .add(new Collider())
                .build());

//        objects.add(GameObject.create()
//                .setPosition(0, 0)
//                .setScale(1, 1)
//                .add(new ColorMaterial(Color.BLACK))
//                .add(Model.Circle(500))
//                .build());
    }


}
