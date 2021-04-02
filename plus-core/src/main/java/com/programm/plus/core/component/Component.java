package com.programm.plus.core.component;

import com.programm.plus.core.obj.GameObject;
import com.programm.plus.maths.Transform2f;
import com.programm.plus.maths.Vector2f;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Component {

    protected GameObject gameObject;
    protected Transform2f transform;

    public final void init(GameObject gameObject){
        this.gameObject = gameObject;
        this.transform = gameObject.getTransform();
    }

    public void onCreate(){}
    public void onDestroy(){}

    public final void move(float vx, float vy){
        Mover mover = gameObject.getComponents().getMover();
        if(mover != null){
            mover.addVelocity(vx, vy);
        }
        else {
            log.debug("!!! GameObject has no mover and will not move.");
        }
    }

    public final void move(Vector2f velocity){
        Mover mover = gameObject.getComponents().getMover();
        if(mover != null){
            mover.addVelocity(velocity);
        }
        else {
            log.debug("!!! GameObject has no mover and will not move.");
        }
    }

    public final <T extends Component> T getComponent(Class<T> cls){
        return gameObject.getComponents().get(cls);
    }

    public final void die(){
        gameObject.die();
    }

    public Component copy() {
        return this;
    }

}
