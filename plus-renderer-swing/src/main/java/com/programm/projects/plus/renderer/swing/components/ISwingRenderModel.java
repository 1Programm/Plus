package com.programm.projects.plus.renderer.swing.components;

import com.programm.projects.plus.renderer.api.components.IRenderModel;

import java.awt.*;

public interface ISwingRenderModel extends IRenderModel {

    void renderFill(Graphics2D g2d);
    void renderBounds(Graphics2D g2d);

}
