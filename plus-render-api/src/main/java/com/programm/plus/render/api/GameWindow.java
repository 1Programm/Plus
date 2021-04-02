package com.programm.plus.render.api;

import com.programm.plus.render.api.controlls.Controlls;
import lombok.Getter;

public abstract class GameWindow {

    @Getter
    private Controlls controlls;

    public GameWindow(){
        this.controlls = initControlls();
    }

    protected abstract Controlls initControlls();

    public abstract void setTitle(String title);
    public abstract void setWidth(int width);
    public abstract void setHeight(int height);
    public abstract void setVisible(boolean visible);

    public abstract String getTitle();
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract boolean isInitialized();
    public abstract boolean shouldClose();

}
