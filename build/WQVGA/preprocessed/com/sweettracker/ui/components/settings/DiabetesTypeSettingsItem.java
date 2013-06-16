package com.sweettracker.ui.components.settings;

import com.sweettracker.model.DiabetesTypeItem;
import com.sweettracker.utils.Utils;
import com.uikit.animations.UikitImageBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.layout.BoxLayout;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Image;

public class DiabetesTypeSettingsItem extends IndicatorSettingsItem {

    private Panel container;

    public DiabetesTypeSettingsItem(int w, String title, String desc, BitmapFont title_font, BitmapFont desc_font, int title_color, int desc_color, Object param, IComponentEventListener cel) {
        super(w, title, desc, title_font, desc_font, title_color, desc_color, param, cel);
    }

    public void addContent() {
        container = new Panel(iWidth, 0);
        container.setLayout(new BoxLayout(UikitConstant.HORIZONTAL, 3));
        DiabetesTypeItem[] items = (DiabetesTypeItem[]) param;
        for (int i = 0; i < items.length; i++) {
            DiabetesTypeItem d = items[i];
            String[] details = Utils.getFormattedDiabetesType(d);
            container.addComponent(new DiabetesItem(iWidth / 3, details[0], details[1], details[2]));
        }

        container.expandToFitContent();
        addComponent(container);
    }

    public void moveIndicatorToIndx(int compIndex) {
        Component c = this.container.componentAt(compIndex);
        super.moveIndicator(c.x + (c.getWidth() / 2));
        super.currentSelIdx = compIndex;
    }

    class DiabetesItem extends Panel {

        public DiabetesItem(int iWidth, String title, String desc, String desc2) {
            super(iWidth, 0);
            setLayout(new BoxLayout(UikitConstant.VERTICAL, 3));
            Image imgText = desc_font.drawStringToImage(title);
            imgText = Utils.replaceColor(imgText, desc_color);
            addComponent(new UikitImageBox(imgText));

            Image imgDesc = desc_font.drawStringToImage(desc);
            imgDesc = Utils.replaceColor(imgDesc, desc_color);
            addComponent(new UikitImageBox(imgDesc));

            Image imgDesc2 = desc_font.drawStringToImage(desc2);
            imgDesc2 = Utils.replaceColor(imgDesc2, desc_color);
            addComponent(new UikitImageBox(imgDesc2));
            expandToFitContent();
        }
    }
}
