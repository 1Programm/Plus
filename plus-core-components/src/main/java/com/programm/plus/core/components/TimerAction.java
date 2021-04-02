package com.programm.plus.core.components;

import com.programm.plus.core.GameContext;
import com.programm.plus.core.component.UpdateComponent;
import com.programm.plus.core.obj.GameObject;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TimerAction extends UpdateComponent {

    public enum Mode {
        REMOVE_SELF,
        REPEAT,
        REINIT
    }

    public static TimerAction RemoveSelf(int time, Runnable action){
        return new TimerAction(Mode.REMOVE_SELF, time, action, null, null);
    }

    public static TimerAction RemoveSelf(int time, Consumer<GameObject> action){
        return new TimerAction(Mode.REMOVE_SELF, time, null, action, null);
    }

    public static TimerAction RemoveSelf(int time, BiConsumer<GameContext, GameObject> action){
        return new TimerAction(Mode.REMOVE_SELF, time, null, null, action);
    }

    public static TimerAction Repeat(int time, Runnable action){
        return new TimerAction(Mode.REPEAT, time, action, null, null);
    }

    public static TimerAction Repeat(int time, Consumer<GameObject> action){
        return new TimerAction(Mode.REPEAT, time, null, action, null);
    }

    public static TimerAction Repeat(int time, BiConsumer<GameContext, GameObject> action){
        return new TimerAction(Mode.REPEAT, time, null, null, action);
    }

    public static TimerAction Repeat(int time, Runnable action, int times){
        return new TimerAction(Mode.REPEAT, time, action, null, null, times);
    }

    public static TimerAction Repeat(int time, Consumer<GameObject> action, int times){
        return new TimerAction(Mode.REPEAT, time, null, action, null, times);
    }

    public static TimerAction Repeat(int time, BiConsumer<GameContext, GameObject> action, int times){
        return new TimerAction(Mode.REPEAT, time, null, null, action, times);
    }

    public static TimerAction Reinit(int time, Runnable action, Supplier<TimerAction> init){
        return new TimerAction(Mode.REINIT, time, action, null, null, init);
    }

    public static TimerAction Reinit(int time, Consumer<GameObject> action, Supplier<TimerAction> init){
        return new TimerAction(Mode.REINIT, time, null, action, null, init);
    }

    public static TimerAction Reinit(int time, BiConsumer<GameContext, GameObject> action, Supplier<TimerAction> init){
        return new TimerAction(Mode.REINIT, time, null, null, action, init);
    }

    private Mode mode;
    private int gTime;
    private Supplier<TimerAction> init;
    private int times = -1;

    private int time;
    private Runnable action1;
    private Consumer<GameObject> action2;
    private BiConsumer<GameContext, GameObject> action3;

    private TimerAction(Mode mode, int time, Runnable action1, Consumer<GameObject> action2, BiConsumer<GameContext, GameObject> action3) {
        this.mode = mode;
        this.gTime = time;
        this.time = time;
        this.action1 = action1;
        this.action2 = action2;
        this.action3 = action3;
    }

    private TimerAction(Mode mode, int time, Runnable action1, Consumer<GameObject> action2, BiConsumer<GameContext, GameObject> action3, Supplier<TimerAction> init) {
        this.mode = mode;
        this.gTime = time;
        this.time = time;
        this.action1 = action1;
        this.action2 = action2;
        this.action3 = action3;
        this.init = init;
    }

    private TimerAction(Mode mode, int time, Runnable action1, Consumer<GameObject> action2, BiConsumer<GameContext, GameObject> action3, int times) {
        this.mode = mode;
        this.gTime = time;
        this.time = time;
        this.action1 = action1;
        this.action2 = action2;
        this.action3 = action3;
        this.times = times;
    }

    @Override
    public void onUpdate(GameContext context) {
        time--;
        if(time <= 0){
            onEnd();
            ComponentUtils.runAll(action1, action2, action3, context, gameObject);
        }
    }

    private void onEnd(){
        if(mode == Mode.REMOVE_SELF) {
            removeComponent();
        }
        else if(mode == Mode.REPEAT){
            if(times >= 0){
                times--;
                if (times == -1){
                    removeComponent();
                }
            }

            time = gTime;
        }
        else if(mode == Mode.REINIT){
            TimerAction nInit = init.get();

            this.mode = nInit.mode;
            this.gTime = nInit.gTime;
            this.init = nInit.init;
            this.times = nInit.times;
            this.time = nInit.time;
            this.action1 = nInit.action1;
            this.action2 = nInit.action2;
            this.action3 = nInit.action3;
        }
    }
}
