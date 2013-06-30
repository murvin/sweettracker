package com.sweettracker.ui.components;

import com.sweettracker.utils.Utils;
import com.uikit.animations.Line;
import com.uikit.animations.UikitImageBox;
import com.uikit.animations.UikitTextBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.ITouchEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.motion.MotionShake;
import com.uikit.styles.TextStyle;
import javax.microedition.lcdui.Image;

public class EntryItem extends Panel implements ITouchEventListener {

    private Image icon;
    private String description, guide;
    private int lineColour;
    private BitmapFont descFont, guideFont;
    private int descColour, guideColor;
    private TextStyle txtBoxStyle;
    private int gap;
    private UikitImageBox imgBoxIcon;
    private UikitTextBox imgBoxDesc;
    public static final int NEXT_SWIPE = 0x0023;
    public static final int PREV_SWIPE = 0x0024;
    private int initialY;
    private UikitImageBox imgBoxGuide;

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

        if (this.guide != null) {
            Image imgGuide = this.guideFont.drawStringToImage(this.guide);
            imgGuide = Utils.replaceColor(imgGuide, guideColor);
            imgBoxGuide = new UikitImageBox(imgGuide);
            imgBoxGuide.x = imgBoxDesc.x;
            imgBoxGuide.y = imgBoxIcon.y + gap + imgBoxDesc.getHeight();
            addComponent(imgBoxGuide);
        }
    }

    private void updateDesc() {
        if (imgBoxDesc != null) {
            removeComponent(imgBoxDesc);
        }
        txtBoxStyle = new TextStyle(descFont);
        txtBoxStyle.setFontColour(descColour);
        imgBoxDesc = new UikitTextBox(iWidth - (gap * 2) - (imgBoxIcon.getWidth() + gap + gap), description, txtBoxStyle);
        imgBoxDesc.x = imgBoxIcon.x + imgBoxIcon.getWidth() + gap;
        imgBoxDesc.y = imgBoxIcon.y;
        addComponent(imgBoxDesc);
    }

    public void setDescription(String description) {
        this.description = description;
        updateDesc();
        if (this.guide != null) {
            imgBoxGuide.y = imgBoxDesc.y + imgBoxDesc.getHeight() + gap;
            if (imgBoxGuide.y + imgBoxGuide.getHeight() > iHeight) {
                expandToFitContent();
            }
        }
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
        return false;
    }
}
