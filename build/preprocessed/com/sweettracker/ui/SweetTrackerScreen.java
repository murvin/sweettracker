package com.sweettracker.ui;

import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.motion.MotionLinear;
import com.uikit.mvc.patterns.Screen;
import com.uikit.painters.PatchPainter;
import javax.microedition.lcdui.Graphics;

public class SweetTrackerScreen extends Screen {

    protected PatchPainter bgPainter;
    protected int patchBorder;
    protected int padding, bottomPadding;

    public SweetTrackerScreen() {
    }
    
    public SweetTrackerScreen(int width, int height) {
        super(width, height);
    }

    protected void drawCurrentFrame(Graphics g) {
        if (bgPainter != null) {
            bgPainter.paint(this, g);
        }
        super.drawCurrentFrame(g);
    }

    public void exit() {
        mfx_slide = new MotionLinear(1, MotionLinear.MOFX_LINEAR_PULLBACK);
        ((MotionLinear) mfx_slide).init(x, y, -UiKitDisplay.getWidth(), y, 10, 5.0f, 0.0f);
        mfx_slide.setMotionFXListener(this);
        this.motionFX = mfx_slide;
    }

    public void exitToRight() {
        mfx_slide = new MotionLinear(1, MotionLinear.MOFX_LINEAR_PULLBACK);
        ((MotionLinear) mfx_slide).init(x, y, UiKitDisplay.getWidth(), y, 10, 5.0f, 0.0f);
        mfx_slide.setMotionFXListener(this);
        this.motionFX = mfx_slide;
    }

    public void enter() {
        mfx_slide = new MotionLinear(1, MotionLinear.MOFX_LINEAR_PULLBACK);
        ((MotionLinear) mfx_slide).init(x, y, 0, y, 10, 5.0f, 0.0f);
        mfx_slide.setMotionFXListener(this);
        this.motionFX = mfx_slide;
    }
}
