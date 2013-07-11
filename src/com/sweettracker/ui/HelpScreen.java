package com.sweettracker.ui;

import com.sweettracker.ui.components.HelpItem;
import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.layout.BoxLayout;
import com.uikit.styles.TextStyle;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class HelpScreen extends SweetTrackerScreen {

    private int vgap;
    private BitmapFont titleFont, descFont;
    private int titleColour, descColour;
    private String[] titles, descs;
    private Image[] images;
    private TextStyle txtDescStyle;

    public HelpScreen() {
        initResources();
        initComponents();
    }

    private void initResources() {
        Image imgFontMed = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_MEDIUM);
        titleFont = new BitmapFont(imgFontMed, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_MEDIUM, 0);
        titleColour = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_TITLE_TEXT_COLOR));

        Image imgFontSmall = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_SMALL);
        descFont = new BitmapFont(imgFontSmall, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_SMALL, 0);
        descColour = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_DESC_TEXT_COLOR));

        padding = 4 * UiKitDisplay.getWidth() / 100;
        vgap = 2 * UiKitDisplay.getHeight() / 100;

        txtDescStyle = new TextStyle(descFont);
        txtDescStyle.setFontColour(descColour);
    }

    private void initComponents() {
        setLayout(new BoxLayout(UikitConstant.VERTICAL, vgap));
        int w = getWidth() - (padding * 2);

        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            String desc = descs[i];
            Image img = images[i];
            addComponent(new HelpItem(w, title, desc, img, titleFont, txtDescStyle, titleColour, descColour));
        }

        updateOffsets();
        getStyle(true).setPadding(topPadding, 0, bottomPadding, 0);
    }
}
