package com.sweettracker.ui.components;

import com.uikit.animations.Line;
import com.uikit.animations.UikitImageBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.Panel;
import javax.microedition.lcdui.Image;

public class EntryItem extends Panel{
    
    private Image icon;
    private String description, guide;
    private int lineColour;
    private BitmapFont descFont, guideFont;
    private int gap;

    public EntryItem(int w, Image icon, String description, String guide, BitmapFont descFont, BitmapFont guideFont, int lineColour, IComponentEventListener cel) {
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
        UikitImageBox imgBoxDesc = new UikitImageBox(imgDesc);
        imgBoxDesc.x = imgBoxIcon.x + imgBoxIcon.getWidth() + gap;
        imgBoxDesc.y = imgBoxIcon.y;
        addComponent(imgBoxDesc);
    }
}
