package main;

import com.programm.projects.core.*;
import com.programm.projects.core.components.ColorMaterial;
import com.programm.projects.core.components.Mover;
import com.programm.projects.plus.engine.api.IEngine;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import com.programm.projects.plus.maths.Vector2f;
import com.programm.projects.plus.renderer.api.events.KeyPressedEvent;
import com.programm.projects.plus.renderer.api.events.KeyReleasedEvent;
import com.programm.projects.plus.renderer.swing.components.SwingShape;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class Main {

    private static class MoveComponent implements IUpdatableComponent {
        private final Vector2f velocity = new Vector2f();
        private final float speed;

        public MoveComponent(float speed) {
            this.speed = speed;
        }

        @Override
        public void init(IEngineContext context) {
            context.events().listenFor(KeyPressedEvent.class, this::onKeyPressed);
            context.events().listenFor(KeyReleasedEvent.class, this::onKeyReleased);
        }

        @Override
        public void update(IGameContext context) {
            GameObject obj = context.getObject();

            Mover mover = obj.getComponent(Mover.class);
            mover.move(velocity);
        }

        private void onKeyPressed(KeyPressedEvent e){
            if(e.isRepeated()) return;

            switch (e.getKeyCode()){
                case KeyPressedEvent.KEY_W:
                    velocity.add(0, -speed);
                    break;
                case KeyPressedEvent.KEY_A:
                    velocity.add(-speed, 0);
                    break;
                case KeyPressedEvent.KEY_S:
                    velocity.add(0, speed);
                    break;
                case KeyPressedEvent.KEY_D:
                    velocity.add(speed, 0);
                    break;
            }
        }

        private void onKeyReleased(KeyReleasedEvent e){
            switch (e.getKeyCode()){
                case KeyPressedEvent.KEY_W:
                    velocity.add(0, speed);
                    break;
                case KeyPressedEvent.KEY_A:
                    velocity.add(speed, 0);
                    break;
                case KeyPressedEvent.KEY_S:
                    velocity.add(0, -speed);
                    break;
                case KeyPressedEvent.KEY_D:
                    velocity.add(-speed, 0);
                    break;
            }
        }
    };

    public static void main(String[] args) throws Exception{
        IEngine engine = new SimpleEngine();
        engine.phase();

        IGameObjectHandler goh = engine.getGOH();
        goh.addObject(GameObject.create()
                .setPosition(100, 100)
                .setScale(5, 5)
                .add(new MoveComponent(5))
                .add(new ColorMaterial(Color.BLACK))
                .add(SwingShape.Rectangle(32, 32))
                .build()
        );
        goh.addObject(GameObject.create()
                .setPosition(100, 200)
                .setScale(4, 4)
//                .add(new MoveComponent(2))
                .add(new ColorMaterial(Color.RED))
                .add(SwingShape.Rectangle(40, 32))
                .build()
        );

        engine.startup();
    }

}
