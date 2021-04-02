package com.programm.plus.render.api;

import com.programm.plus.camera.api.Camera;
import com.programm.plus.maths.Vector2f;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public abstract class Pencil {

    public static String PAINT_MODE_OUTLINE = "paint_mode_outline";
    public static String PAINT_MODE_FILL = "paint_mode_fill";

    @Setter private String paintMode;

    protected GameWindow window;
    protected Camera camera;

    protected Pencil(String paintMode){
        this.paintMode = paintMode;
    }

    public void init(GameWindow window, Camera camera){
        this.window = window;
        this.camera = camera;
    }

    public void drawRect(float x, float y, float width, float height){
        x -= getCameraX();
        y -= getCameraY();

        if(paintMode == null){
            throw new IllegalStateException("Paint Mode of null is invalid.");
        }
        else if(paintMode.equals(PAINT_MODE_OUTLINE)){
            outlineRect(x, y, width, height);
        }
        else if(paintMode.equals(PAINT_MODE_FILL)){
            fillRect(x, y, width, height);
        }
        else {
            log.warn("Paint Mode '{}' is not supported.", paintMode);
        }
    }

    public void drawCircle(float x, float y, float radius){
        Vector2f camPos = camera.getPosition();

        x -= getCameraX();
        y -= getCameraY();

        if(paintMode == null){
            throw new IllegalStateException("Paint Mode of null is invalid.");
        }
        else if(paintMode.equals(PAINT_MODE_OUTLINE)){
            outlineCircle(x, y, radius);
        }
        else if(paintMode.equals(PAINT_MODE_FILL)){
            fillCircle(x, y, radius);
        }
        else {
            log.warn("Paint Mode '{}' is not supported.", paintMode);
        }
    }

    protected abstract void outlineRect(float x, float y, float width, float height);

    protected abstract void fillRect(float x, float y, float width, float height);

    protected abstract void outlineCircle(float x, float y, float radius);

    protected abstract void fillCircle(float x, float y, float radius);

    public abstract void setColor(Color color);

    public abstract void clearWindow(Color clearColor);

    private float getCameraX(){
        float cX = camera.getPosition().getX();

        return cX - window.getWidth()/2.0f;
    }

    private float getCameraY(){
        float cY = camera.getPosition().getY();

        return cY - window.getHeight()/2.0f;
    }

}
