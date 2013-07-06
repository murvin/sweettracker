package com.sweettracker.ui;

import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.motion.MotionLinear;
import com.uikit.mvc.patterns.Screen;
import com.uikit.painters.PatchPainter;

public abstract class SweetTrackerScreen extends Screen {

    protected PatchPainter bgPainter;
    protected int patchBorder;
    protected int padding, bottomPadding, topPadding;

    public SweetTrackerScreen() {
    }

    public SweetTrackerScreen(int width, int height) {
        super(width, height);
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

    public void onEnter() {
        
    }

    public void onExit() {
    }

    protected void updateOffsets() {
        topPadding = Resources.getInstance().getThemeImage(GraphicsResources.IMG_BAR_BG).getHeight();
        bottomPadding = (UiKitDisplay.getHeight() - (topPadding * 2)) - iHeight;
        if (bottomPadding < 0) {
            bottomPadding = 0;
            setBottomOffset(topPadding);
        } else {
            setIsScrollable(false);
        }
    }
}
