package com.sweettracker.ui.components;

import com.sweettracker.utils.Utils;
import com.uikit.animations.UikitImageBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Panel;
import com.uikit.layout.BoxLayout;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Image;

public class LevelEntryItem extends Panel {

    private int vgap, padding;
    private String unit, guide;
    private float level;
    private BitmapFont descFont, guideFont;
    private int descColour, guideColour;
    private Image imgLevel, imgUnit, imgGuide;

    public LevelEntryItem(int iWidth, int iHeight, float level, String unit, String guide,
            BitmapFont descFont, BitmapFont guideFont,
            int descColour, int guideColour) {
        super(iWidth, iHeight);
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
        imgLevel = this.descFont.drawStringToImage("" + this.level);
        imgLevel = Utils.replaceColor(imgLevel, descColour);
        imgUnit = this.guideFont.drawStringToImage(this.unit);
        imgUnit = Utils.replaceColor(imgUnit, guideColour);
        imgGuide = this.guideFont.drawStringToImage(this.guide);
        imgGuide = Utils.replaceColor(imgGuide, guideColour);
    }

    private void initComponents() {
        setLayout(new BoxLayout(UikitConstant.VERTICAL, vgap));
        UikitImageBox imgBoxLevel = new UikitImageBox(imgLevel);
        addComponent(imgBoxLevel);

        UikitImageBox imgBoxUnit = new UikitImageBox(imgUnit);
        addComponent(imgBoxUnit);

        UikitImageBox imgBoxGuide = new UikitImageBox(imgGuide);
        addComponent(imgBoxGuide);
        
        getStyle(true).setPadding(padding, 0, padding, 0);
    }
}
