package com.sweettracker.ui;

import com.sweettracker.ui.components.TermsDelimiter;
import com.sweettracker.utils.GlobalResources;
import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;
import com.uikit.animations.UikitTextBox;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.layout.BoxLayout;
import com.uikit.styles.TextStyle;
import com.uikit.utils.UikitConstant;
import com.uikit.coreElements.BitmapFont;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class TermsAndConditionsScreen extends SweetTrackerScreen {

    private String[] titles;
    private String[] description;
    private BitmapFont largeFont, smallFont;
    private int lineColour;
    private TextStyle txtBoxStyle;
    private int vgap;
    private int fontColour;

    public TermsAndConditionsScreen() {
        initResources();
        initComponents();
    }

    private void initResources() {
        titles = new String[]{
            Resources.getInstance().getText(GlobalResources.TXT_TERMS_HEADER_1),
            Resources.getInstance().getText(GlobalResources.TXT_TERMS_HEADER_2),
            Resources.getInstance().getText(GlobalResources.TXT_TERMS_HEADER_3),
            Resources.getInstance().getText(GlobalResources.TXT_TERMS_HEADER_4)
        };
        description = new String[]{
            Resources.getInstance().getText(GlobalResources.TXT_TERMS_DESC_1),
            Resources.getInstance().getText(GlobalResources.TXT_TERMS_DESC_2),
            Resources.getInstance().getText(GlobalResources.TXT_TERMS_DESC_3),
            Resources.getInstance().getText(GlobalResources.TXT_TERMS_DESC_4)
        };

        Image imgFont = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_LARGE);
        largeFont = new BitmapFont(imgFont, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_LARGE, 0);

        Image imgFontSmall = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_SMALL);
        smallFont = new BitmapFont(imgFontSmall, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_SMALL, 0);

        lineColour = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_LINE_SEPERATOR_COLOR));
        padding = 4 * UiKitDisplay.getWidth() / 100;

        vgap = 1 * UiKitDisplay.getHeight() / 100;

        txtBoxStyle = new TextStyle(smallFont);
        txtBoxStyle.setFontColour(fontColour);

    }

    private void initComponents() {
        setLayout(new BoxLayout(UikitConstant.VERTICAL, vgap * 4));
        int w = iWidth - (padding * 2);

        for (int i = 0; i < titles.length; i++) {
            addComponent(new TermsDelimiter(titles[i], w, largeFont.getHeight(), lineColour, largeFont));
            addComponent(new UikitTextBox(w, description[i], txtBoxStyle));
        }

        updateOffsets();
        getStyle(true).setPadding(topPadding + vgap, 0, bottomPadding + vgap, 0);
    }
}
