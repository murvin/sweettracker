package com.uikit.mvc.patterns;

import com.uikit.motion.IMotionListener;
import com.uikit.motion.Motion;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.SmartPanel;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.IFocusable;

public abstract class Screen extends SmartPanel
        implements IComponentEventListener, IMotionListener {

    public static final int EVENT_ENTER_FINISHED = 0x0101;
    public static final int EVENT_EXIT_FINISHED = 0x0102;
    protected Motion mfx_slide_enter, mfx_slide_exit;
    protected Controller controller;

    public Screen() {
        super(UiKitDisplay.getWidth(), UiKitDisplay.getHeight());
    }

    public Screen(int width, int height) {
        super(width, height);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public abstract void enter();

    public abstract void exit();

    public abstract void exitToRight();

    public abstract void onEnter();

    public abstract void onExit();

    protected void notifyExitFinished() {
        if (cel != null) {
            cel.onComponentEvent(this, EVENT_EXIT_FINISHED, null, 0);
        }
        onExit();
    }

    protected void notifyEnterFinished() {
        if (cel != null) {
            cel.onComponentEvent(this, EVENT_ENTER_FINISHED, null, 0);
        }
        onEnter();
    }

    protected void freeResources() {
        removeAllComponents();
    }

    protected void activate() {
        if (getCurrentFocusable() != null) {
            ((IFocusable) getCurrentFocusable()).onFocus();
        }
    }

    protected void deactivate() {
        if (getCurrentFocusable() != null) {
            ((IFocusable) getCurrentFocusable()).onDefocus();
        }
    }

    public void onComponentEvent(Component c, int e, Object o, int p) {
    }

    public void onMotionStarted(Motion mfx) {
    }

    public void onMotionFinished(Motion mfx) {
        if (mfx == mfx_slide_exit) {
            notifyExitFinished();
        } else {
            notifyEnterFinished();
        }
    }

    public void onMotionProgressed(Motion mfx, int i) {
    }
}
