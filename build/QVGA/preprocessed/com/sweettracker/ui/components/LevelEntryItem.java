package com.sweettracker.ui.components;

import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;
import com.uikit.animations.UikitImageBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.ITouchEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.layout.BoxLayout;
import com.uikit.motion.MotionShake;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Image;

public class LevelEntryItem extends Panel implements ITouchEventListener {

    private int vgap, padding;
    private String unit, guide;
    private double level;
    private BitmapFont descFont, guideFont;
    private int descColour, guideColour;
    private Image imgLevel, imgUnit, imgGuide, imgBloodIcon;
    private UikitImageBox imgBoxDrop;
    private Panel levelContainer;
    private UikitImageBox imgBoxLevel;

    public LevelEntryItem(int iWidth, int iHeight, double level, String unit, String guide,
            BitmapFont descFont, BitmapFont guideFont,
            int descColour, int guideColour, IComponentEventListener cel) {
        super(iWidth, iHeight);
        this.cel = cel;
        this.level = level;
        this.unit = unit;
        this.guide = guide;
        this.descColour = descColour;
        this.guideColour = guideColour;
        this.descFont = descFont;
        this.guideFont = guideFont;

        initResources();
        initComponents();
    }

    private void initResources() {
        vgap = 3;
        padding = 20;
        imgUnit = this.descFont.drawStringToImage(this.unit);
        imgUnit = Utils.replaceColor(imgUnit, descColour);
        imgGuide = this.guideFont.drawStringToImage(this.guide);
        imgGuide = Utils.replaceColor(imgGuide, guideColour);
        imgBloodIcon = Resources.getInstance().getThemeImage(GraphicsResources.IMG_DROP_ICON);
    }

    private void initComponents() {
        setLayout(new BoxLayout(UikitConstant.VERTICAL, vgap));
        levelContainer = new Panel(0, 0);
        levelContainer.setLayout(new BoxLayout(UikitConstant.HORIZONTAL, vgap));

        imgBoxDrop = new UikitImageBox(imgBloodIcon);
        levelContainer.addComponent(imgBoxDrop);

        updateLevel();
        addComponent(levelContainer);

        UikitImageBox imgBoxUnit = new UikitImageBox(imgUnit);
        addComponent(imgBoxUnit);

        UikitImageBox imgBoxGuide = new UikitImageBox(imgGuide);
        addComponent(imgBoxGuide);

        getStyle(true).setPadding(padding, 0, padding, 0);
    }

    public void shakeIconImage() {
        MotionShake motionShake = new MotionShake(2);
        motionShake.init(imgBoxDrop.x, imgBoxDrop.y, 15, 0, 3);
        imgBoxDrop.motionFX = motionShake;
    }

    private void updateLevel() {
        if (imgBoxLevel == null) {
            imgLevel = this.descFont.drawStringToImage("" + this.level);
            imgLevel = Utils.replaceColor(imgLevel, descColour);
            imgBoxLevel = new UikitImageBox(imgLevel);
            levelContainer.addComponent(imgBoxLevel);
            levelContainer.expandToFitContent();
        } else {
            imgLevel = this.descFont.drawStringToImage("" + this.level);
            imgLevel = Utils.replaceColor(imgLevel, descColour);
            imgBoxLevel.setImage(imgLevel);
            levelContainer.expandToFitContent();
        }
    }

    public void setLevel(double level) {
        this.level = level;
        updateLevel();
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
