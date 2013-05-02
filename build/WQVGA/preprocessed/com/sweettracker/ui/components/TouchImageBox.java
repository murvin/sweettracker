package com.sweettracker.ui.components;

import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.uikit.animations.UikitImageBox;
import com.uikit.coreElements.ITouchEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.painters.PatchPainter;
import javax.microedition.lcdui.Image;

public class TouchImageBox extends Panel implements ITouchEventListener {

    public static final int EVENT_IMAGE_CLICK = 0xf001;
    private int id;
    private PatchPainter highLightPainter;

    public TouchImageBox(Image image, int delta) {
        super(image.getWidth() + (delta * 2), image.getHeight() + (delta * 2));
        UikitImageBox imgBox = new UikitImageBox(image);
        imgBox.x = delta;
        imgBox.y = delta;
        addComponent(imgBox);
        Image imgHighlight = Resources.getInstance().getThemeImage(GraphicsResources.IMG_HIGH_BG);
        int patchMargin = 3;
        highLightPainter = new PatchPainter(imgHighlight, patchMargin, patchMargin, patchMargin, patchMargin);
    }

    public void setId(int id) {
        this.id = id;
    }

    private void focus(boolean isOnFocus) {
        if (isOnFocus) {
            getStyle(true).addRenderer(highLightPainter);
        } else {
            getStyle().getRenderers().removeElement(highLightPainter);
        }
    }

    private void updateListener() {
        if (cel != null) {
            cel.onComponentEvent(this, EVENT_IMAGE_CLICK, null, id);
        }
    }

    public boolean onPress(int type, int x, int y) {
        if (type == ITouchEventListener.SINGLE_PRESS) {
            focus(false);
            updateListener();
            return true;
        } else if (type == ITouchEventListener.TOUCH_DOWN) {
            focus(true);
        } else if (type == ITouchEventListener.TOUCH_RELEASE) {
            focus(false);
        } else if (type == ITouchEventListener.LONG_PRESS) {
            focus(false);
        } else {
            focus(false);
        }
        return false;
    }

    public boolean onDrag(int type, int startX, int startY, int deltaX, int deltaY) {
        focus(false);
        return false;
    }
}
