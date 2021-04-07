package main.components;

import com.programm.projects.core.GameObject;
import com.programm.projects.core.IEngineContext;
import com.programm.projects.core.IGameContext;
import com.programm.projects.core.IUpdatableComponent;
import com.programm.projects.core.components.Mover;
import com.programm.projects.plus.maths.Vector2f;
import com.programm.projects.plus.renderer.api.events.KeyPressedEvent;
import com.programm.projects.plus.renderer.api.events.KeyReleasedEvent;
import com.programm.projects.plus.renderer.api.events.MouseScrolledEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeyboardController implements IUpdatableComponent {

    private final Vector2f velocity = new Vector2f();
    private final float speed;

    public KeyboardController(float speed) {
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
}
