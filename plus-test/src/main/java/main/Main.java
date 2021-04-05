package main;

import com.programm.projects.core.GameObject;
import com.programm.projects.core.IComponent;
import com.programm.projects.core.IGameContext;
import com.programm.projects.core.IUpdatableComponent;
import com.programm.projects.core.components.ColorMaterial;
import com.programm.projects.core.components.Mover;
import com.programm.projects.plus.engine.api.IEngine;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import com.programm.projects.plus.renderer.swing.components.SwingShape;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.geom.Rectangle2D;

@Slf4j
public class Main {

    private static IComponent moveComponent = new IUpdatableComponent() {
        @Override
        public void init() {

        }

        @Override
        public void update(IGameContext context) {
            GameObject obj = context.getObject();

            Mover mover = obj.getComponent(Mover.class);
            mover.move(1, 0.3f);
        }
    };

    public static void main(String[] args) throws Exception{
        IEngine engine = new SimpleEngine();
        IGameObjectHandler goh = engine.getGOH();
        goh.addObject(GameObject.create()
                .setPosition(100, 100)
                .setScale(4, 4)
                .add(moveComponent)
                .add(new ColorMaterial(Color.WHITE))
                .add(new SwingShape(new Rectangle2D.Float(-16, -16, 16, 16)))
                .build()
        );

        engine.startup();
    }

}
