package com.sweettracker.ui.components;

import com.uikit.coreElements.Component;
import javax.microedition.lcdui.Graphics;

public class ChartBar extends Component {

    private int fillColour;
    private int currentLevel;
    private int step;

    public ChartBar(int iWidth, int iHeight, int fillColour, int totalSteps) {
        super(iWidth, iHeight);
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

        // fill
        g.setColor(this.fillColour);
        g.fillRect(1, currentLevel + 1, iWidth - 1, iHeight - (currentLevel + 1));
    }
}
