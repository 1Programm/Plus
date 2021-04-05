package com.programm.projects.plus.renderer.swing;

import com.programm.projects.core.GameObject;
import com.programm.projects.core.IObjectBatch;

import java.awt.*;
import java.awt.image.BufferStrategy;

class SwingCanvas extends Canvas {

    public void render(IObjectBatch renderableBatch, Color backgroundColor){
        BufferStrategy bs = getBufferStrategy();

        if(bs == null){
            createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);
        for(GameObject obj : renderableBatch){
            int x = (int)obj.getPosition().getX();
            int y = (int)obj.getPosition().getY();
            int width = (int)obj.getSize().getX();
            int height = (int)obj.getSize().getY();

            g.drawRect(x, y, width, height);
        }

        g.dispose();
        bs.show();
    }

}
