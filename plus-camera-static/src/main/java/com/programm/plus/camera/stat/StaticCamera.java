package com.programm.plus.camera.stat;

import com.programm.plus.camera.api.Camera;
import com.programm.plus.core.GameContext;
import com.programm.plus.maths.Vector2f;

public class StaticCamera extends Camera {

    public StaticCamera() {}

    public StaticCamera(Vector2f position) {
        super(position);
    }

    @Override
    public void onUpdate(GameContext gameContext) {}

}
