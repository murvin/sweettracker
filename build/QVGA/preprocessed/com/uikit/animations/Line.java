package com.uikit.animations;

import com.uikit.coreElements.Component;
import javax.microedition.lcdui.Graphics;

public class Line extends Component {

    private int color;

    public Line(int w, int color) {
        super(w, 1);
        this.color = color;
    }

    protected void drawCurrentFrame(Graphics g) {
        super.drawCurrentFrame(g);
        g.setColor(color);
        g.drawLine(0, 0, iWidth, 0);
    }
}
