package com.sweettracker.ui.components;

import com.sweettracker.utils.Utils;
import com.uikit.animations.Line;
import com.uikit.animations.UikitImageBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.ITouchEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.motion.MotionShake;
import javax.microedition.lcdui.Image;

public class EntryItem extends Panel implements ITouchEventListener {

    private Image icon;
    private String description, guide;
    private int lineColour;
    private BitmapFont descFont, guideFont;
    private int descColour, guideColor;
    private int gap;
    private UikitImageBox imgBoxIcon;
    private UikitImageBox imgBoxDesc;
    public static final int NEXT_SWIPE = 0x0023;
    public static final int PREV_SWIPE = 0x0024;
    private int initialY;

    public EntryItem(int w, Image icon,
            String description,
            String guide,
            BitmapFont descFont, BitmapFont guideFont,
            int descColour, int guideColour, int lineColour,
            IComponentEventListener cel) {
        super(w, 30);
        this.cel = cel;
        setLayout(null);
        gap = 5;
        getStyle(true).setPadding(gap);
        this.icon = icon;
        this.description = description;
        this.guide = guide;
        this.descFont = descFont;
        this.guideFont = guideFont;
        this.descColour = descColour;
        this.guideColor = guideColour;
        this.lineColour = lineColour;
        addElements();
        expandToFitContent();
    }

    private void addElements() {
        Line line = new Line(iWidth - (gap * 2), lineColour);
        line.x = gap;
        line.y = gap;
        addComponent(line);

        imgBoxIcon = new UikitImageBox(icon);
        imgBoxIcon.x = line.x;
        imgBoxIcon.y = line.y + line.getHeight() + gap;
        initialY = imgBoxIcon.y;
        addComponent(imgBoxIcon);

        updateDesc();

        Image imgGuide = this.guideFont.drawStringToImage(this.guide);
        imgGuide = Utils.replaceColor(imgGuide, guideColor);
        UikitImageBox imgBoxGuide = new UikitImageBox(imgGuide);
        imgBoxGuide.x = imgBoxDesc.x;
        imgBoxGuide.y = imgBoxIcon.y + gap + imgBoxDesc.getHeight();
        addComponent(imgBoxGuide);
    }

    private void updateDesc() {
        if (imgBoxDesc == null) {
            Image imgDesc = this.descFont.drawStringToImage(this.description);
            imgDesc = Utils.replaceColor(imgDesc, descColour);
            imgBoxDesc = new UikitImageBox(imgDesc);
            imgBoxDesc.x = imgBoxIcon.x + imgBoxIcon.getWidth() + gap;
            imgBoxDesc.y = imgBoxIcon.y;
            addComponent(imgBoxDesc);
        } else {
            Image imgDesc = this.descFont.drawStringToImage(this.description);
            imgDesc = Utils.replaceColor(imgDesc, descColour);
            imgBoxDesc.setImage(imgDesc);
        }
    }

    public void setDescription(String description) {
        this.description = description;
        updateDesc();
    }

    public void shakeIconImage() {
        MotionShake motionShake = new MotionShake(2);
        motionShake.init(imgBoxIcon.x, initialY, 15, 0, 3);
        imgBoxIcon.motionFX = motionShake;
    }

    public boolean onPress(int type, int x, int y) {
        if (type == ITouchEventListener.SINGLE_PRESS) {
            if (cel != null) {
                cel.onComponentEvent(this, type, null, -1);
                return true;
            }
        }
        return false;
    }

    public boolean onDrag(int type, int startX, int startY, int deltaX, int deltaY) {
        if (cel != null) {
            if (type == ITouchEventListener.DRAG_RELEASE) {
                boolean isNext = false;
                if (Math.abs(deltaX) >= 1) {
                    if (deltaX < 0) {
                        isNext = true;
                    }
                    cel.onComponentEvent(this, type, null, isNext ? NEXT_SWIPE : PREV_SWIPE);
                    return true;
                }
            }
        }
        return false;
    }
}
