package com.programm.plus.engine.tests.bricks;

import com.programm.plus.collision.api.Collider;
import com.programm.plus.collision.api.CollisionListener;
import com.programm.plus.core.GameContext;
import com.programm.plus.core.components.SimpleRectangleDrawer;
import com.programm.plus.core.components.SimpleVelocityMover;
import com.programm.plus.core.components.WindowCollisionListener;
import com.programm.plus.core.components.material.ColorMaterial;
import com.programm.plus.core.obj.GameObject;
import com.programm.plus.core.obj.ObjectInstance;
import com.programm.plus.maths.Vector2f;

import java.awt.*;

public class Ball extends ObjectInstance implements CollisionListener {

    public Ball() {
        this.tag(BricksMain.BALL);
        this.addComponent(new ColorMaterial(Color.BLUE));
        this.addComponent(new SimpleRectangleDrawer());
        this.addComponent(new Collider(this));
        this.addComponent(new WindowCollisionListener()
                .setLeftCollisionListener(this::wndLeft)
                .setRightCollisionListener(this::wndRight));
    }

    @Override
    public void onCollision(GameObject ball, GameObject other) {
        if(other.hasTag(BricksMain.PANEL)){
            SimpleVelocityMover velMover = ball.getComponents().get(SimpleVelocityMover.class);
            float speed = velMover.getVelocity().length();

            Vector2f midBall = ball.getTransform().getPosition().clone().add(ball.getTransform().getSize().clone().mul(0.5f));
            Vector2f midOther = other.getTransform().getPosition().clone().add(other.getTransform().getSize().clone().mul(0.5f));

            Vector2f dist = Vector2f.Sub(midBall, midOther);
            dist.mul(0.5f, 1);
            dist.normalize().mul(speed);

            velMover.getVelocity().set(dist);
        }
        else if(other.hasTag(BricksMain.BRICK)){
            SimpleVelocityMover velMover = ball.getComponents().get(SimpleVelocityMover.class);
            velMover.getVelocity().mul(1, -1);
        }
    }

    private void wndLeft(GameContext context, GameObject obj){
        obj.getTransform().getPosition().setX(0);

        SimpleVelocityMover velMover = obj.getComponents().get(SimpleVelocityMover.class);

        velMover.getVelocity().mul(-1, 1);
    }

    private void wndRight(GameContext context, GameObject obj){
        obj.getTransform().getPosition().setX(context.getWindow().getWidth() - obj.getTransform().getSize().getX());

        SimpleVelocityMover velMover = obj.getComponents().get(SimpleVelocityMover.class);

        velMover.getVelocity().mul(-1, 1);
    }
}
