package com.sweettracker.ui.components;

import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.ITouchEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.motion.IMotionListener;
import com.uikit.motion.Motion;
import com.uikit.motion.MotionEaseOutExpo;
import com.uikit.utils.ImageUtil;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Chart extends Panel implements IMotionListener, ITouchEventListener {

    private int[] colours, days;
    private float[] levels;
    private int axisColour;
    private float target;
    private MotionEaseOutExpo mfx_slide;
    private BitmapFont font;
    private String xAxisLabel, yAxisLabel;
    private Image imgXaxisLabel, imgYaxisLabel;
    private int startingOffset;
    private float maxLevel, minLevel;
    private int maxDays;
    public static final int EXIT_FINISHED = 0x1201;
    public static final int ENTER_FINISHED = 0x1202;

    public Chart(int iWidth, int iHeight, float[] levels, int[] colours, int[] days, float target, int xAxisLength,
            int axisColour, BitmapFont font, String xAxisLabel, String yAxisLabel, float maxLevel, float minLevel, int maxDays, IComponentEventListener cel) {
        super(iWidth, iHeight);
        this.levels = levels;
        this.colours = colours;
        this.days = days;
        this.cel = cel;
        this.target = target;
        this.font = font;
        this.axisColour = axisColour;

        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.maxLevel = maxLevel;
        this.minLevel = minLevel;
        this.maxDays = maxDays;

        initResources();
        addBarCharts();
    }

    private void initResources() {
        startingOffset = 15;
        imgXaxisLabel = font.drawStringToImage(xAxisLabel);
        imgXaxisLabel = ImageUtil.replaceColor(imgXaxisLabel, this.axisColour);

        imgYaxisLabel = font.drawStringToImage(yAxisLabel);
        imgYaxisLabel = ImageUtil.replaceColor(imgYaxisLabel, this.axisColour);
    }

    private void addBarCharts() {
        if (levels != null) {
            int barWidth = 5;
            for (int i = 0; i < levels.length; i++) {
                float level = levels[i];
                int colour = colours[i];
                int day = days[i];

                int height = (int) (level * 100.0f / maxLevel);
                int xCoor = (int) (iWidth * day / maxLevel);
                xCoor -= 2;
                ChartBar bar = new ChartBar(barWidth, height, axisColour, colour);
                bar.x = xCoor;
                bar.y = iHeight - height;
                addComponent(bar);
            }
        }
    }

    protected void drawCurrentFrame(Graphics g) {
        super.drawCurrentFrame(g);

        // Axes
        g.setColor(axisColour);
        g.drawLine(startingOffset, startingOffset, startingOffset, iHeight - (startingOffset / 2));
        g.drawLine(startingOffset / 2, iHeight - startingOffset, iWidth - startingOffset, iHeight - startingOffset);

        // Axes's labels
        g.drawImage(imgYaxisLabel, startingOffset / 2, 0, 20);
        g.drawImage(imgXaxisLabel, iWidth - imgXaxisLabel.getWidth() - startingOffset, iHeight - imgXaxisLabel.getHeight(), 20);

        // draw levels

    }

    public void exit() {
        mfx_slide = new MotionEaseOutExpo(1, 4);
        mfx_slide.init(x, y, -UiKitDisplay.getWidth(), y, 15);
        mfx_slide.setMotionFXListener(this);
        this.motionFX = mfx_slide;
    }

    public void exitToRight() {
        mfx_slide = new MotionEaseOutExpo(1, 4);
        mfx_slide.init(x, y, UiKitDisplay.getWidth(), y, 15);
        mfx_slide.setMotionFXListener(this);
        this.motionFX = mfx_slide;
    }

    public void enter() {
        mfx_slide = new MotionEaseOutExpo(1, 4);
        mfx_slide.init(x, y, 0, y, 15);
        mfx_slide.setMotionFXListener(this);
        this.motionFX = mfx_slide;
    }

    public void onMotionStarted(Motion mfx) {
    }

    public void onMotionProgressed(Motion mfx, int progress) {
    }

    public void onMotionFinished(Motion mfx) {
        if (x != 0) {
            if (cel != null) {
                cel.onComponentEvent(this, EXIT_FINISHED, null, -1);
            }
        } else {
            if (cel != null) {
                cel.onComponentEvent(this, ENTER_FINISHED, null, -1);
            }
        }
    }

    public boolean onPress(int type, int iX, int iY) {
        return true;
    }

    public boolean onDrag(int type, int iStartX, int iStartY, int iDeltaX, int iDeltaY) {
        return false;
    }
}
