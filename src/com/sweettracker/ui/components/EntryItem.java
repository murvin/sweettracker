package com.sweettracker.ui.components;

import com.sweettracker.utils.Utils;
import com.uikit.animations.Line;
import com.uikit.animations.UikitImageBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.ITouchEventListener;
import com.uikit.coreElements.Panel;
import javax.microedition.lcdui.Image;

public class EntryItem extends Panel implements ITouchEventListener{
    
    private Image icon;
    private String description, guide;
    private int lineColour;
    private BitmapFont descFont, guideFont;
    private int descColour, guideColor;
    private int gap;

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
    
    private void addElements(){
        Line line = new Line(iWidth - (gap * 2), lineColour);
        line.x = gap;
        line.y = gap;
        addComponent(line);
        
        UikitImageBox imgBoxIcon = new UikitImageBox(icon);
        imgBoxIcon.x = line.x;
        imgBoxIcon.y = line.y + line.getHeight() + gap;
        addComponent(imgBoxIcon);
        
        Image imgDesc = this.descFont.drawStringToImage(this.description);
        imgDesc = Utils.replaceColor(imgDesc, descColour);
        UikitImageBox imgBoxDesc = new UikitImageBox(imgDesc);
        imgBoxDesc.x = imgBoxIcon.x + imgBoxIcon.getWidth() + gap;
        imgBoxDesc.y = imgBoxIcon.y;
        addComponent(imgBoxDesc);
        
        Image imgGuide = this.guideFont.drawStringToImage(this.guide);
        imgGuide = Utils.replaceColor(imgGuide, guideColor);
        UikitImageBox imgBoxGuide = new UikitImageBox(imgGuide);
        imgBoxGuide.x = imgBoxDesc.x;
        imgBoxGuide.y = imgBoxIcon.y + gap + imgBoxDesc.getHeight();
        addComponent(imgBoxGuide);
    }

    public boolean onPress(int type, int x, int y) {
        return false;
    }

    public boolean onDrag(int type, int startX, int startY, int deltaX, int deltaY) {
        return false;
    }
}
