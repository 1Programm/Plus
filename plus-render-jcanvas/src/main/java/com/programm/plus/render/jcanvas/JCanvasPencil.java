package com.programm.plus.render.jcanvas;

import com.programm.plus.render.api.Pencil;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.function.Supplier;

@Slf4j
public class JCanvasPencil extends Pencil {

    private Supplier<Graphics> gfxSupplier;
    private Color color;

    public JCanvasPencil(Supplier<Graphics> gfxSupplier){
        super(Pencil.PAINT_MODE_OUTLINE);

        this.gfxSupplier = gfxSupplier;
        this.color = Color.WHITE;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void clearWindow(Color clearColor) {
        Graphics g = gfxSupplier.get();

        g.setColor(clearColor);
        g.fillRect(0, 0, window.getWidth(), window.getHeight());
    }

    @Override
    protected void outlineRect(float x, float y, float width, float height){
        Graphics g = gfxSupplier.get();

        g.setColor(color);
        g.drawRect((int)x, (int)y, (int)width, (int)height);

    }

    @Override
    protected void fillRect(float x, float y, float width, float height){
        Graphics g = gfxSupplier.get();

        g.setColor(color);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
    }

    @Override
    protected void outlineCircle(float x, float y, float radius){
        Graphics g = gfxSupplier.get();

        g.setColor(color);
        g.drawOval((int)x, (int)y, (int)radius, (int)radius);
    }

    @Override
    protected void fillCircle(float x, float y, float radius){
        Graphics g = gfxSupplier.get();

        g.setColor(color);
        g.fillOval((int)x, (int)y, (int)radius, (int)radius);
    }
}
