package com.sweettracker.ui.components;

import com.uikit.animations.UikitImageBox;
import com.uikit.animations.UikitTextBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Panel;
import com.uikit.layout.BoxLayout;
import com.uikit.styles.TextStyle;
import com.uikit.utils.ImageUtil;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Image;

public class HelpItem extends Panel {

    private int layout_gap;
    private String title, description;
    private Image imgTitle, imgHelp;
    private BitmapFont titleFont;
    private int titleColour, descColour;
    private TextStyle txtDescStyle;

    public HelpItem(int iWidth, String title, String description, Image imgHelp, BitmapFont titleFont, TextStyle txtDescStyle, int titleColour, int descColour) {
        super(iWidth, 0);
        this.title = title;
        this.description = description;
        this.imgHelp = imgHelp;
        this.titleFont = titleFont;
        this.txtDescStyle = txtDescStyle;

        initResources();
        initComponents();
        expandToFitContent();
    }

    private void initResources() {
        layout_gap = 5;
        imgTitle = titleFont.drawStringToImage(title);
        imgTitle = ImageUtil.replaceColor(imgTitle, titleColour);
    }

    private void initComponents() {
        addComponent(new TermsDelimiter(title, iWidth, 30, descColour, titleFont, descColour));
        addComponent(new UikitTextBox(iWidth, description, txtDescStyle));
        if (imgHelp != null){
            addComponent(new UikitImageBox(imgHelp));
        }
        setLayout(new BoxLayout(UikitConstant.VERTICAL, layout_gap));
    }
}
