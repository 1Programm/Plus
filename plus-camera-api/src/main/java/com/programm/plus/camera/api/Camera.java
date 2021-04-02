package com.programm.plus.camera.api;

import com.programm.plus.core.GameContext;
import com.programm.plus.maths.Vector2f;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public abstract class Camera {

    @Getter @Setter protected Vector2f position;

    public abstract void onUpdate(GameContext context);

}
