package com.programm.plus.render.jcanvas;

import javax.swing.*;
import java.awt.*;

public class FrameUtils {

    public static JFrame createWindow(Canvas canvas, String title, int width, int height){
        Dimension d = new Dimension(width, height);

        JFrame frame = new JFrame(title);
        //frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        canvas.setPreferredSize(d);
        canvas.setMaximumSize(d);
        canvas.setMinimumSize(d);

        frame.add(canvas);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);

        return frame;
    }

}
