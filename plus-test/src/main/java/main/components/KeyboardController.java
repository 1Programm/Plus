package main.components;

import com.programm.projects.core.IEngineContext;
import com.programm.projects.core.IGameContext;
import com.programm.projects.core.IUpdatableComponent;

public class KeyboardController implements IUpdatableComponent {

    private final float speed;
    private float vx, vy;

    public KeyboardController(float speed) {
        this.speed = speed;
    }

    @Override
    public void init(IEngineContext context) {
        //...
    }

    @Override
    public void update(IGameContext context) {

    }
}
