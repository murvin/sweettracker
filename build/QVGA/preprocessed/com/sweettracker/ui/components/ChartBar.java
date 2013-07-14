package com.sweettracker.ui.components;

import com.uikit.coreElements.Component;
import javax.microedition.lcdui.Graphics;

public class ChartBar extends Component {

    private int borderColour, fillColour;
    private int currentLevel;
    private int step;

    public ChartBar(int iWidth, int iHeight, int borderColour, int fillColour, int totalSteps) {
        super(iWidth, iHeight);
        this.borderColour = borderColour;
        this.fillColour = fillColour;
        this.currentLevel = iHeight;
        step = iHeight / totalSteps;
    }

    public void step() {
        if (currentLevel > 0 && (currentLevel - step) > 0) {
            currentLevel -= step;
        } else {
            currentLevel = 0;
        }
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
        g.fillRect(1, currentLevel + 1, iWidth - 1, iHeight - (currentLevel + 1));
    }
}
