package com.sweettracker.ui.components;

import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.uikit.motion.MotionLinear;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.coreElements.Panel;

import com.uikit.painters.PatchPainter;
import javax.microedition.lcdui.Image;

public class MenuBar extends Panel {

    private Image bg;
    private MenuBarItem lsk, rsk;
    private int patch;
    private int softKeyPadding;

    public MenuBar() {
        super(UiKitDisplay.getWidth(), 0);
        initResources();
        initComponents();

        setSize(iWidth, bg.getHeight());
    }

    public void removeSoftKey(boolean isLSK) {
        if (isLSK) {
            if (this.lsk != null && this.lsk.getContainingPanel() == this) {
                removeComponent(lsk);
            }
        } else {
            if (this.rsk != null && this.rsk.getContainingPanel() == this) {
                removeComponent(rsk);
            }
        }
    }

    private void initResources() {
        patch = 5;
        this.bg = Resources.getInstance().getThemeImage(GraphicsResources.IMG_MENUBAR_TILE);
        this.softKeyPadding = 3 * UiKitDisplay.getWidth() / 100;
    }

    private void initComponents() {
        getStyle(true).addRenderer(new PatchPainter(bg, patch, patch, patch, patch));
    }

    public void setLsk(String text, int id) {
        if (this.lsk == null) {
            lsk = new MenuBarItem(bg.getHeight(), true);
            lsk.setEventListener(cel);
        }

        if (lsk.getContainingPanel() == null) {
            addComponent(lsk);
        }

        lsk.setText(text);
        lsk.y = (iHeight - lsk.getHeight()) / 2;
        lsk.x = softKeyPadding;
        lsk.setId(id);
    }

    public void setRsk(String text, int id) {
        if (this.rsk == null) {
            rsk = new MenuBarItem(bg.getHeight(), false);
            rsk.setEventListener(cel);
        }

        if (rsk.getContainingPanel() == null) {
            addComponent(rsk);
        }

        rsk.setText(text);
        rsk.y = (iHeight - rsk.getHeight()) / 2;
        rsk.x = iWidth - rsk.getWidth() - softKeyPadding;
        rsk.setId(id);
    }

    public void enter() {
        MotionLinear mfx_slide = new MotionLinear(1, MotionLinear.MOFX_LINEAR_PULLBACK);
        ((MotionLinear) mfx_slide).init(x, y, 0, y, 10, 0.0f, 0.0f);
        this.motionFX = mfx_slide;
    }
}
