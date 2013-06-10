package com.sweettracker.ui.components.settings;

import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.uikit.animations.UikitImageBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.motion.MotionLinear;
import javax.microedition.lcdui.Image;

public abstract class IndicatorSettingsItem extends SettingsItem implements IComponentEventListener {

    private UikitImageBox imgBoxIndicator;
    protected int currentSelIdx;

    public IndicatorSettingsItem(int w, String title, String desc, BitmapFont title_font, BitmapFont desc_font, int title_color, int desc_color, Object param, IComponentEventListener cel) {
        super(w, title, desc, title_font, desc_font, title_color, desc_color, param, cel);
    }

    protected void initComponents() {
        super.initComponents();
        addIndicator();
    }

    protected void addIndicator() {
        Image imageIndicator = Resources.getInstance().getThemeImage(GraphicsResources.IMG_INDICATOR);
        imgBoxIndicator = new UikitImageBox(imageIndicator);
        addComponent(imgBoxIndicator);
    }

    public void moveIndicator(int x_pos) {
        MotionLinear mfx_slide = new MotionLinear(1, MotionLinear.MOFX_LINEAR_PULLBACK);
        ((MotionLinear) mfx_slide).init(imgBoxIndicator.x, imgBoxIndicator.y, x_pos - imgBoxIndicator.getWidth() / 2, imgBoxIndicator.y, 10, 5.0f, 0.0f);
        imgBoxIndicator.motionFX = mfx_slide;
    }

    public void onComponentEvent(Component c, int e, Object o, int index) {
        moveIndicator(c.x + (c.getWidth() / 2));
    }
    
    public int getCurrentSelIdx(){
        return currentSelIdx;
    }
}
