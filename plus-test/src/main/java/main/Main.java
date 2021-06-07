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
                .setPosition(0, 0)
                .add(new MoveReverseOnCollisionComponent(50, 0))
                .add(new Collider())
                .add(new Camera())
                .add(new ColorMaterial(Color.BLACK))
                .add(Model.Rectangle(32, 32))
                .build());

        objects.add(GameObject.create()
                .setPosition(-200, 0)
                .add(new Collider())
                .add(new ColorMaterial(Color.RED))
                .add(Model.Rectangle(32, 32))
                .build());

        objects.add(GameObject.create()
                .setPosition(200, 0)
                .add(new Collider())
                .add(new ColorMaterial(Color.RED))
                .add(Model.Rectangle(32, 32))
                .build());

//        int size = 10;
//        for(int x=-size;x<size;x++){
//            for(int y=-size;y<size;y++){
//                if(x != -size && x != size-1 && y != -size && y != size-1) continue;
//
//                objects.add(GameObject.create()
//                        .setPosition(x * 32, y * 32)
//                        .add(new Collider())
//                        .add(new ColorMaterial(Color.RED))
//                        .add(Model.Rectangle(32, 32))
//                        .build());
//            }
//        }
    }


}
