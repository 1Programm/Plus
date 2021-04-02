package com.programm.plus.render.jcanvas;

import com.programm.plus.render.api.GameWindow;
import com.programm.plus.render.api.Pencil;
import com.programm.plus.render.api.Renderer;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

@Slf4j
public class JCanvasRenderer implements Renderer {

    private JGameWindow gameWindow;

    private Canvas canvas;
    private int buffer;

    private BufferStrategy bs;
    private Graphics gfx;

    public JCanvasRenderer(int buffer){
        this.canvas = new Canvas();
        this.buffer = buffer;
    }

    @Override
    public GameWindow initWindow(String title, int width, int height) {
        JFrame frame = FrameUtils.createWindow(canvas, title, width, height);
        this.gameWindow = new JGameWindow(frame, canvas);

        return gameWindow;
    }

    @Override
    public Pencil initPencil() {
        return new JCanvasPencil(this::getGraphics);
    }

    @Override
    public void preRender() {
        bs = canvas.getBufferStrategy();

        if(bs == null){
            canvas.createBufferStrategy(buffer);

            bs = canvas.getBufferStrategy();

            if(bs == null){
                log.error("BufferStrategy stays null!");
                System.exit(1);
            }
        }

        gfx = bs.getDrawGraphics();
    }

    @Override
    public void postRender() {
        gfx.dispose();
        bs.show();
    }

    @Override
    public void cleanUp() {
        gameWindow.dispose();
    }

    private Graphics getGraphics(){
        return gfx;
    }
}
