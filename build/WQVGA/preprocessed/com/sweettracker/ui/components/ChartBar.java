package com.sweettracker.ui.components;

import com.uikit.coreElements.Component;
import javax.microedition.lcdui.Graphics;

public class ChartBar extends Component {

    private int borderColour, fillColour;
    private int currentLevel;

    public ChartBar(int iWidth, int iHeight, int borderColour, int fillColour) {
        super(iWidth, iHeight);
        this.borderColour = borderColour;
        this.fillColour = fillColour;
        this.currentLevel = 0;
    }

    public void step() {
    }

    protected void drawCurrentFrame(Graphics g) {
        super.drawCurrentFrame(g);

        // Borders
        g.setColor(this.borderColour);
        g.drawLine(0, iHeight, 0, currentLevel);
        g.drawLine(0, currentLevel, iWidth, currentLevel);
        g.drawLine(iWidth, currentLevel, iWidth, iHeight);

        // fill
        g.setColor(this.fillColour);
        g.fillRect(0, currentLevel, iWidth, iHeight);
    }
}
