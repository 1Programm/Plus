package com.programm.plus.engine.tests.tests;

import com.programm.plus.collision.api.Collider;
import com.programm.plus.collision.api.CollisionListener;
import com.programm.plus.core.component.Mover;
import com.programm.plus.core.components.KeyboardMover;
import com.programm.plus.core.components.SimpleRectangleDrawer;
import com.programm.plus.core.components.material.ColorMaterial;
import com.programm.plus.core.obj.GameObject;
import com.programm.plus.engine.simple.SimpleEngine;
import com.programm.plus.goh.api.GameObjectHandler;

import java.awt.*;

public class CollisionTest {

    public static void main(String[] args) {

        /* -------------------------------- */
        /* Creating new Simple Engine       */
        /* -------------------------------- */

        SimpleEngine engine = new SimpleEngine();


        /* ---------------------------------------------- */
        /* Simple Engine Components setup                 */
        /* -> Enabling default collision (AABB Collision) */
        /* ---------------------------------------------- */

        engine.enableCollision();


        /* ------------------------------------------- */
        /* Engine init - Creating (not visible) window */
        /* ------------------------------------------- */

        engine.init("Test Collision", 600, 500);


        /* ------------------------ */
        /* Getting GOH from context */
        /* ------------------------ */

        GameObjectHandler goh = engine
                .getContext()
                .getObjectHandler();


        /* ------------------- */
        /* Creating GameObject */
        /* ------------------- */

        goh.createEmpty()
                .setPosition(10, 100) // Setting Position
                .setSize(20, 20) // Setting Width and Height
                .addComponent(new Mover()) // Adding Mover - Only Objects with a Mover can be moved
                .addComponent(new Collider()) // Adding Collider - Only Objects with a Collider can collide
                .addComponent(new ColorMaterial(Color.RED, Color.BLACK)) // Material which will be used to draw object in specific colors
                .addComponent(new SimpleRectangleDrawer()) // Component that will draw a Rectangle
                .addComponent(new KeyboardMover(200)) // Component which controls this object through wasd - keys
        .build(); // Build adds the Object to the Game


        /* ------------------------------ */
        /* Creating 2nd Simple GameObject */
        /* ------------------------------ */

        goh.createEmpty()
                .setPosition(100, 300)
                .setSize(32, 32)
                .addComponent(new Mover())
                .addComponent(new Collider()
                        .addListener(new ColorChangeOnCollision(Color.GREEN)))
                .addComponent(new ColorMaterial(Color.GRAY, Color.BLACK))
                .addComponent(new SimpleRectangleDrawer())
        .build();


        /* ------------------------------------------- */
        /* Starting the engine - making window visible */
        /* ------------------------------------------- */

        engine.start();
    }

    private static class ColorChangeOnCollision implements CollisionListener {

        private Color color;
        private boolean changed;

        ColorChangeOnCollision(Color color){
            this.color = color;
        }

        @Override
        public void onCollision(GameObject me, GameObject other) {
            if(!changed) {
                changed = true;
                ColorMaterial material = me.getComponents().get(ColorMaterial.class);
                material.setBgColor(color);
            }
        }
    }

}
