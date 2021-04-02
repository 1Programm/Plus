package com.programm.plus.collision.aabb;

import com.programm.plus.collision.api.CollisionInfo;
import com.programm.plus.maths.Vector2f;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AABBCollisionInfo extends CollisionInfo {

    private Vector2f offset;

}
