package com.sweettracker.ui.components.settings;

import com.sweettracker.ui.components.TouchImageBox;
import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.layout.BoxLayout;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Image;

public class ThemeSettingsItem extends IndicatorSettingsItem {

    private Image imgMale, imgFemale;
    private Panel container;

    public ThemeSettingsItem(int w, String title, String desc, BitmapFont title_font, BitmapFont desc_font, int title_color, int desc_color, Object param, IComponentEventListener _cel) {
        super(w, title, desc, title_font, desc_font, title_color, desc_color, param, _cel);
    }

    public void addContent() {
        imgMale = Resources.getInstance().getThemeImage(GraphicsResources.IMG_MALE);
        imgFemale = Resources.getInstance().getThemeImage(GraphicsResources.IMG_FEMALE);
        container = new Panel(iWidth, 0);

        TouchImageBox imgBoxMale = new TouchImageBox(imgMale, 5);
        imgBoxMale.setEventListener(this);
        container.addComponent(imgBoxMale);

        TouchImageBox imgBoxFemale = new TouchImageBox(imgFemale, 5);
        imgBoxFemale.setEventListener(this);
        container.addComponent(imgBoxFemale);

        container.setLayout(new BoxLayout(UikitConstant.HORIZONTAL, (iWidth - (imgBoxFemale.getWidth() * 2)) / 2));
        container.expandToFitContent();
        addComponent(container);
    }

    public void moveIndicatorToIndx(int compIndex) {
        Component c = this.container.componentAt(compIndex);
        super.moveIndicator(c.x + (c.getWidth() / 2));
        super.currentSelIdx = compIndex;
    }
}
