package com.programm.plus.core.components;

import com.programm.plus.core.GameContext;
import com.programm.plus.core.obj.GameObject;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

class ComponentUtils {

    public static void runEither(Runnable action1, Consumer<GameObject> action2, BiConsumer<GameContext, GameObject> action3, GameContext context, GameObject obj){
        if(action1 != null){
            action1.run();
        }
        else if(action2 != null){
            action2.accept(obj);
        }
        else if(action3 != null){
            action3.accept(context, obj);
        }
    }

    public static void runAll(Runnable action1, Consumer<GameObject> action2, BiConsumer<GameContext, GameObject> action3, GameContext context, GameObject obj){
        if(action1 != null){
            action1.run();
        }
        if(action2 != null){
            action2.accept(obj);
        }
        if(action3 != null){
            action3.accept(context, obj);
        }
    }

}
