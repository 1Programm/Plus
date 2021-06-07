package main;

import com.programm.projects.plus.collision.api.Collider;
import com.programm.projects.plus.collision.api.ICollisionListener;
import com.programm.projects.plus.core.*;
import com.programm.projects.plus.core.components.Mover;
import com.programm.projects.plus.maths.Vector1f;
import com.programm.projects.plus.maths.Vector2f;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveReverseOnCollisionComponent extends AbstractComponent implements IUpdatableComponent {

    private final Vector2f vel = new Vector2f();
    private Mover mover;

    public MoveReverseOnCollisionComponent(float vx, float vy) {
        this.vel.set(vx, vy);
    }

    @Override
    public void init(IEngineContext context) {
        mover = parent.getComponent(Mover.class);
        Collider collider = parent.getComponent(Collider.class);
        collider.listen(this::onCollision);
    }

    @Override
    public void update(IGameContext context) {
        float vx = (float) (vel.getX() * context.getDelta());
        float vy = (float) (vel.getY() * context.getDelta());

        mover.move(vx, vy);

    }

    private void onCollision(GameObject other, Vector2f intersectionNormal) {
        log.info("Collision: [{}]", intersectionNormal);
        float inx = intersectionNormal.getX();
        float iny = intersectionNormal.getY();

        inx = inx < 0 ? inx : -inx;
        iny = iny < 0 ? iny : -iny;

        inx = inx == 0 ? 1 : inx;
        iny = iny == 0 ? 1 : iny;

        vel.mul(inx, iny);
    }
}
