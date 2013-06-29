package com.sweettracker.ui.components.settings;

import com.sweettracker.ui.components.TouchImageBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.layout.BoxLayout;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Image;

public class LanguageSettingsItem extends IndicatorSettingsItem {

    private Panel flagsPanel;

    public LanguageSettingsItem(int w, String title, String desc, BitmapFont title_font, BitmapFont desc_font, int title_color, int desc_color, Image[] flags, IComponentEventListener cel) {
        super(w, title, desc, title_font, desc_font, title_color, desc_color, flags, cel);
    }

    private Component getFlagItem(Image flag_image) {
        TouchImageBox imageBox = new TouchImageBox(flag_image, 3);
        imageBox.setEventListener(this);
        return imageBox;
    }

    public void addContent() {
        Image[] flags = (Image[]) param;
        flagsPanel = new Panel(iWidth, flags[0].getHeight());
        int layout_gap = 10;
        BoxLayout flagsPanelLayout = new BoxLayout(UikitConstant.HORIZONTAL, layout_gap);
        for (int i = 0; i < flags.length; i++) {
            flagsPanel.addComponent(getFlagItem(flags[i]));
        }
        flagsPanel.setLayout(flagsPanelLayout);
        flagsPanel.expandToFitContent();
        addComponent(flagsPanel);
    }

    public void moveIndicatorToIndx(int compIndex) {
        Component c = this.flagsPanel.componentAt(compIndex);
        super.moveIndicator(c.x + (c.getWidth() / 2));
        super.currentSelIdx = compIndex;
    }
}
