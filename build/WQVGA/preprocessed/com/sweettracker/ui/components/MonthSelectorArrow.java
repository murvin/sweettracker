package com.sweettracker.ui.components;

import com.uikit.animations.UikitImageBox;
import com.uikit.coreElements.IFocusable;
import com.uikit.coreElements.ITouchEventListener;

import javax.microedition.lcdui.Image;

public class MonthSelectorArrow extends UikitImageBox implements IFocusable, ITouchEventListener {

    private boolean isOnFocus;

    public MonthSelectorArrow(Image image) {
        super(image);
    }

    public void onFocus() {
        isOnFocus = true;
    }

    public void onDefocus() {
        isOnFocus = false;
    }

    public boolean isFocused() {
        return isOnFocus;
    }

    private void updateListener() {
        if (cel != null) {
            cel.onComponentEvent(this, -1, null, -1);
        }
    }

    public boolean onPress(int type, int iX, int iY) {
        if (type == ITouchEventListener.SINGLE_PRESS) {
            updateListener();
            return true;
        }
        return false;
    }

    public boolean onDrag(int type, int iStartX, int iStartY, int iDeltaX, int iDeltaY) {
        return true;
    }
}
