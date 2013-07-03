package com.sweettracker.ui.components;

import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.ITouchEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.motion.IMotionListener;
import com.uikit.motion.Motion;
import com.uikit.motion.MotionEaseOutExpo;
import javax.microedition.lcdui.Graphics;

public class Chart extends Panel implements IMotionListener, ITouchEventListener {

    private float[] normal, high, critical;
    private int axisColour, targetColour;
    private float target;
    private int xAxisLength;
    private MotionEaseOutExpo mfx_slide;
    private BitmapFont font;
    private int startingOffset;
    public static final int EXIT_FINISHED = 0x1201;
    public static final int ENTER_FINISHED = 0x1202;

    public Chart(int iWidth, int iHeight, float[] normal, float[] high, float[] critical, float target, int xAxisLength,
            int axisColour, int targetColour, BitmapFont font, IComponentEventListener cel) {
        super(iWidth, iHeight);
        this.cel = cel;
        this.normal = normal;
        this.high = high;
        this.critical = critical;
        this.target = target;
        this.axisColour = axisColour;
        this.xAxisLength = xAxisLength;
        this.targetColour = targetColour;

        initResources();
        addBarCharts();
    }

    private void initResources() {
        startingOffset = 15;
    }

    private void addBarCharts() {
    }

    protected void drawCurrentFrame(Graphics g) {
        super.drawCurrentFrame(g);

        // Axes
        g.setColor(axisColour);
        g.drawLine(startingOffset, startingOffset, startingOffset, iHeight - (startingOffset / 2));
        g.drawLine(startingOffset / 2, iHeight - startingOffset, iWidth - startingOffset, iHeight - startingOffset );

        //Axes's labels
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
