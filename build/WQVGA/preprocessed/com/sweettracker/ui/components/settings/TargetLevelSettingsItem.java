package com.sweettracker.ui.components.settings;

import com.sweettracker.utils.Utils;
import com.uikit.animations.UikitButton;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.utils.UikitConstant;

public class TargetLevelSettingsItem extends SettingsItem {
    public static final int BUTTON_ID = 0x1122;

    public TargetLevelSettingsItem(int w, String title, String desc, BitmapFont title_font, BitmapFont desc_font, int title_color, int desc_color, Object param, IComponentEventListener cel) {
        super(w, title, desc, title_font, desc_font, title_color, desc_color, param, cel);
    }

    public void addContent() {

        UikitButton btnPinCode = Utils.getButton(((Float) param).toString(), iWidth / 2);
        btnPinCode.setId(BUTTON_ID);
        btnPinCode.setEventListener(this.cel);

        Panel container = new Panel(iWidth, btnPinCode.getHeight());
        container.getStyle(true).setPadding(5);
        container.addComponent(btnPinCode);
        btnPinCode.setPosition(UikitConstant.HCENTER | UikitConstant.VCENTER);
        container.expandToFitContent();
        addComponent(container);
    }
}
