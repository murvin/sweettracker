package com.sweettracker.ui.components;

import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;
import com.uikit.animations.UikitImageBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Panel;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.motion.MotionLinear;
import com.uikit.painters.BgImagePainter;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class NavigationBar extends Panel {

    private Image imgLabel, imgTile;
    private MotionLinear mfx_slide;
    private BitmapFont font;
    private int txtColor;
    private UikitImageBox imageBox;

    public NavigationBar(int w) {
        super(w, 0);
        x = -w;

        initResources();
        initComponents();
    }

    private void initResources() {
        Image imgFont = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_LARGE);
        font = new BitmapFont(imgFont, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_LARGE, 0);
        txtColor = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_TITLE_LIGHT_TEXT_COLOR));

        imgTile = Resources.getInstance().getThemeImage(GraphicsResources.IMG_BAR_BG);
    }

    private void initComponents() {
        setSize(iWidth, imgTile.getHeight());
        setLayout(null);
        getStyle(true).addRenderer(new BgImagePainter(imgTile, UikitConstant.XREPEAT));
    }

    public void setLabel(String label) {
        imgLabel = font.drawStringToImage(label);
        imgLabel = Utils.replaceColor(imgLabel, txtColor);
        if (imageBox == null) {
            imageBox = new UikitImageBox(imgLabel);
            addComponent(imageBox);
        }

        imageBox.setImage(imgLabel);
        imageBox.x = (iWidth - imageBox.getWidth()) / 2;
        imageBox.y = (iHeight - imageBox.getHeight()) / 2;
    }

    public void exit() {
        mfx_slide = new MotionLinear(1, MotionLinear.MOFX_LINEAR_PULLBACK);
        ((MotionLinear) mfx_slide).init(x, y, -UiKitDisplay.getWidth(), y, 10, 5.0f, 0.0f);
        this.motionFX = mfx_slide;
    }

    public void enter() {
        mfx_slide = new MotionLinear(1, MotionLinear.MOFX_LINEAR_PULLBACK);
        ((MotionLinear) mfx_slide).init(x, y, 0, y, 10, 5.0f, 0.0f);
        this.motionFX = mfx_slide;
    }
}
