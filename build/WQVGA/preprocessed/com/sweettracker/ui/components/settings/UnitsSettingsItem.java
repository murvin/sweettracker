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

public class UnitsSettingsItem extends IndicatorSettingsItem {
    
    private Panel container;

    public UnitsSettingsItem(int w, String title, String desc, BitmapFont title_font, BitmapFont desc_font, int title_color, int desc_color, Object param, IComponentEventListener cel) {
        super(w, title, desc, title_font, desc_font, title_color, desc_color, param, cel);
    }

    public void addContent() {
        Image imgUnitMg = Resources.getInstance().getThemeImage(GraphicsResources.IMG_UNITS_MG);
        Image imgUnitMmol = Resources.getInstance().getThemeImage(GraphicsResources.IMG_UNITS_MMOL);

        container = new Panel(iWidth, 0);
        int delta = 3;
        TouchImageBox imgBoxMg = new TouchImageBox(imgUnitMg, delta);
        imgBoxMg.setEventListener(this);
        container.addComponent(imgBoxMg);
        TouchImageBox imgBoxMmol = new TouchImageBox(imgUnitMmol, delta);
        imgBoxMmol.setEventListener(this);
        container.addComponent(imgBoxMmol);

        int layout_gap = (iWidth - (imgUnitMg.getWidth() + imgUnitMmol.getWidth())) / 3;
        BoxLayout flagsPanelLayout = new BoxLayout(UikitConstant.HORIZONTAL, layout_gap);
        container.setLayout(flagsPanelLayout);
        container.expandToFitContent();
        addComponent(container);
    }
    
     public void moveIndicatorToIndx (int compIndex){
        Component c = this.container.componentAt(compIndex);
        super.moveIndicator(c.x + (c.getWidth() / 2));
        super.currentSelIdx = compIndex;
    }
}
