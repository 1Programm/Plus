package com.programm.plus.core.components;

import com.programm.plus.core.GameContext;
import com.programm.plus.core.component.Component;
import com.programm.plus.core.obj.GameObject;
import com.programm.plus.core.component.UpdateComponent;
import com.programm.plus.maths.Vector2f;

import java.util.function.BiConsumer;

public class WindowCollisionListener extends UpdateComponent {

    private Runnable allCollisionListener;
    private Runnable topCollisionListener;
    private Runnable leftCollisionListener;
    private Runnable rightCollisionListener;
    private Runnable bottomCollisionListener;

    private BiConsumer<GameContext, GameObject> _allCollisionListener;
    private BiConsumer<GameContext, GameObject> _topCollisionListener;
    private BiConsumer<GameContext, GameObject> _leftCollisionListener;
    private BiConsumer<GameContext, GameObject> _rightCollisionListener;
    private BiConsumer<GameContext, GameObject> _bottomCollisionListener;

    public WindowCollisionListener(){
        this.allCollisionListener = null;
    }

    public WindowCollisionListener(Runnable allCollisionListener){
        this.allCollisionListener = allCollisionListener;
    }

    public WindowCollisionListener(BiConsumer<GameContext, GameObject> allCollisionListener){
        this._allCollisionListener = allCollisionListener;
    }

    public WindowCollisionListener(Runnable topCollisionListener, Runnable leftCollisionListener, Runnable rightCollisionListener, Runnable bottomCollisionListener) {
        this.topCollisionListener = topCollisionListener;
        this.leftCollisionListener = leftCollisionListener;
        this.rightCollisionListener = rightCollisionListener;
        this.bottomCollisionListener = bottomCollisionListener;
    }

    WindowCollisionListener(Runnable allCollisionListener, Runnable topCollisionListener,
                            Runnable leftCollisionListener, Runnable rightCollisionListener,
                            Runnable bottomCollisionListener,
                            BiConsumer<GameContext, GameObject> _allCollisionListener, BiConsumer<GameContext, GameObject> _topCollisionListener,
                            BiConsumer<GameContext, GameObject> _leftCollisionListener, BiConsumer<GameContext, GameObject> _rightCollisionListener,
                            BiConsumer<GameContext, GameObject> _bottomCollisionListener) {
        this.allCollisionListener = allCollisionListener;
        this.topCollisionListener = topCollisionListener;
        this.leftCollisionListener = leftCollisionListener;
        this.rightCollisionListener = rightCollisionListener;
        this.bottomCollisionListener = bottomCollisionListener;
        this._allCollisionListener = _allCollisionListener;
        this._topCollisionListener = _topCollisionListener;
        this._leftCollisionListener = _leftCollisionListener;
        this._rightCollisionListener = _rightCollisionListener;
        this._bottomCollisionListener = _bottomCollisionListener;
    }

    @Override
    public void onUpdate(GameContext context) {
        Vector2f pos = transform.getPosition();
        Vector2f size = transform.getSize();

        if(pos.getX() < 0){
            callCollisionListener(leftCollisionListener, _leftCollisionListener, context);
        }
        else if(pos.getX() + size.getX() > context.getWindow().getWidth()){
            callCollisionListener(rightCollisionListener, _rightCollisionListener, context);
        }

        if(pos.getY() < 0){
            callCollisionListener(topCollisionListener, _topCollisionListener, context);
        }
        else if(pos.getY() + size.getY() > context.getWindow().getHeight()){
            callCollisionListener(bottomCollisionListener, _bottomCollisionListener, context);
        }
    }

    @Override
    public Component copy() {
        return new WindowCollisionListener(
                this.allCollisionListener,
                this.topCollisionListener,
                this.leftCollisionListener,
                this.rightCollisionListener,
                this.bottomCollisionListener,
                this._allCollisionListener,
                this._topCollisionListener,
                this._leftCollisionListener,
                this._rightCollisionListener,
                this._bottomCollisionListener
        );
    }

    private void callCollisionListener(Runnable listener, BiConsumer<GameContext, GameObject> _listener, GameContext context){
        if(allCollisionListener != null){
            allCollisionListener.run();
        }
        else if(_allCollisionListener != null){
            _allCollisionListener.accept(context, gameObject);
        }

        if(listener != null){
            listener.run();
        }

        if(_listener != null){
            _listener.accept(context, gameObject);
        }
    }

    public WindowCollisionListener setTopCollisionListener(Runnable topCollisionListener) {
        this.topCollisionListener = topCollisionListener;
        return this;
    }

    public WindowCollisionListener setLeftCollisionListener(Runnable leftCollisionListener) {
        this.leftCollisionListener = leftCollisionListener;
        return this;
    }

    public WindowCollisionListener setRightCollisionListener(Runnable rightCollisionListener) {
        this.rightCollisionListener = rightCollisionListener;
        return this;
    }

    public WindowCollisionListener setBottomCollisionListener(Runnable bottomCollisionListener) {
        this.bottomCollisionListener = bottomCollisionListener;
        return this;
    }

    public WindowCollisionListener setTopCollisionListener(BiConsumer<GameContext, GameObject> topCollisionListener) {
        this._topCollisionListener = topCollisionListener;
        return this;
    }

    public WindowCollisionListener setLeftCollisionListener(BiConsumer<GameContext, GameObject> leftCollisionListener) {
        this._leftCollisionListener = leftCollisionListener;
        return this;
    }

    public WindowCollisionListener setRightCollisionListener(BiConsumer<GameContext, GameObject> rightCollisionListener) {
        this._rightCollisionListener = rightCollisionListener;
        return this;
    }

    public WindowCollisionListener setBottomCollisionListener(BiConsumer<GameContext, GameObject> bottomCollisionListener) {
        this._bottomCollisionListener = bottomCollisionListener;
        return this;
    }
}
