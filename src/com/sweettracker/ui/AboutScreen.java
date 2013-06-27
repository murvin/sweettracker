package com.sweettracker.ui;

import com.sweettracker.utils.GlobalResources;
import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;
import com.uikit.animations.UikitImageBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.layout.BoxLayout;
import com.uikit.styles.ComponentStyle;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class AboutScreen extends SweetTrackerScreen {

    private Image imgLogo;
    private Image imgAppNameTitle, imgAppName;
    private Image imgVersionNameTitle, imgVersionName;
    private Image imgDeveloperNameTitle, imgDeveloperName;
    private BitmapFont fontTitle, fontDesc;
    private int colourTitle;
    private int vgap;
    private ComponentStyle titleStyle;

    public AboutScreen() {
        initResources();
        initComponents();
    }

    private void initResources() {

        Image imgFont = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_LARGE);
        fontTitle = new BitmapFont(imgFont, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_LARGE, 0);

        Image imgFontDesc = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_SMALL);
        fontDesc = new BitmapFont(imgFontDesc, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_SMALL, 0);

        colourTitle = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_TITLE_TEXT_COLOR));

        padding = 4 * UiKitDisplay.getWidth() / 100;
        vgap = 1 * UiKitDisplay.getHeight() / 100;

        imgLogo = Resources.getInstance().getThemeImage(GraphicsResources.IMG_ABOUT_ICON);

        String title = Resources.getInstance().getText(GlobalResources.TXT_APP_NAME);
        imgAppNameTitle = fontTitle.drawStringToImage(title, colourTitle);
        title = UiKitDisplay.midlet.getAppProperty("MIDlet-Name");

        imgAppName = fontDesc.drawStringToImage(title);

        title = Resources.getInstance().getText(GlobalResources.TXT_ABOUT_VERSION);
        imgVersionNameTitle = fontTitle.drawStringToImage(title, colourTitle);
        title = UiKitDisplay.midlet.getAppProperty("MIDlet-Version");
        imgVersionName = fontDesc.drawStringToImage(title);


        title = Resources.getInstance().getText(GlobalResources.TXT_ABOUT_DEVELOPER);
        imgDeveloperNameTitle = fontTitle.drawStringToImage(title, colourTitle);
        title = UiKitDisplay.midlet.getAppProperty("MIDlet-Vendor");

        imgDeveloperName = fontDesc.drawStringToImage(title);

        titleStyle = new ComponentStyle();
        titleStyle.setPadding(vgap, 0, 0, 0);
    }

    private void initComponents() {
        ((BoxLayout) getLayout()).setGap(vgap);
        UikitImageBox imgBoxLogo = new UikitImageBox(imgLogo);
        ComponentStyle st = new ComponentStyle();
        st.setPadding(padding);
        imgBoxLogo.setStyle(st);
        addComponent(imgBoxLogo);

        addComponent(new UikitImageBox(0, 0, imgAppNameTitle, titleStyle));
        addComponent(new UikitImageBox(imgAppName));

        addComponent(new UikitImageBox(0, 0, imgVersionNameTitle, titleStyle));
        addComponent(new UikitImageBox(imgVersionName));

        addComponent(new UikitImageBox(0, 0, imgDeveloperNameTitle, titleStyle));
        addComponent(new UikitImageBox(imgDeveloperName));
        updateOffsets();
        getStyle(true).setPadding(topPadding, 0, bottomPadding, 0);
    }
}
