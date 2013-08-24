package com.sweettracker.ui;

import com.sweettracker.model.Settings;
import com.sweettracker.ui.components.EntrySelectionItem;
import com.sweettracker.utils.Database;
import com.sweettracker.utils.GlobalResources;
import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;
import com.uikit.coreElements.BitmapFont;
import com.uikit.utils.UikitConstant;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.layout.BoxLayout;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class LocaleScreen extends SweetTrackerScreen {

    private Settings settings;
    private int selectedIndex;
    private int vgap;
    private Image imgCheckBoxOn, imageCheckBoxOff;
    private String[] titles;
    private Image[] icons;
    private BitmapFont medFont;
    private int fontColour;

    public LocaleScreen() {
        try {
            settings = (Settings) Database.getInstance().retrieveISerializable(Database.SETTINGS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (settings != null) {
            selectedIndex = settings.getCurrentLocale();
        }
        initResources();
        initComponents();
    }

    protected final void initResources() {
        Image imgFontMed = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_MEDIUM);
        medFont = new BitmapFont(imgFontMed, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_MEDIUM, 0);

        fontColour = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_DESC_TEXT_COLOR));

        padding = 4 * UiKitDisplay.getWidth() / 100;
        vgap = 2 * UiKitDisplay.getHeight() / 100;

        imgCheckBoxOn = Resources.getInstance().getThemeImage(GraphicsResources.IMG_CHECKBOX_ON);
        imageCheckBoxOff = Resources.getInstance().getThemeImage(GraphicsResources.IMG_CHECKBOX_OFF);

        titles = new String[]{
            Resources.getInstance().getText(GlobalResources.LOCALE_ITALIAN),
            Resources.getInstance().getText(GlobalResources.LOCALE_FRENCH),
            Resources.getInstance().getText(GlobalResources.LOCALE_SPANISH),
            Resources.getInstance().getText(GlobalResources.LOCALE_ENGLISH),
            Resources.getInstance().getText(GlobalResources.LOCALE_GERMAN),};

        icons = new Image[]{
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_FLAG_IT),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_FLAG_FR),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_FLAG_ES),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_FLAG_EN),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_FLAG_DE),};

    }

    public void onComponentEvent(Component c, int e, Object o, int p) {
        if (e == EntrySelectionItem.EVENT_CHECKED) {
            ((EntrySelectionItem) componentAt(selectedIndex)).setIsChecked(false);
            selectedIndex = p;
        }
    }

    public void saveLocale() {
        settings.setCurrentLocale(selectedIndex);
        try {
            Database.getInstance().saveISerializable(settings, Database.SETTINGS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int getSelectedIndex(){
        return this.selectedIndex;
    }

    private void initComponents() {
        setLayout(new BoxLayout(UikitConstant.VERTICAL, vgap));
        int w = getWidth() - (padding * 2);
        int h = icons[0].getHeight() + (vgap * 2);

        addAllEntrySelectionItems(w, h, titles, icons);

        updateOffsets();
        getStyle(true).setPadding(topPadding, 0, bottomPadding, 0);
    }

    protected void addAllEntrySelectionItems(int w, int h, String[] titles, Image[] icons) {
        EntrySelectionItem item = null;
        Image image = null;
        for (int i = 0; i < titles.length; i++) {
            if (icons != null) {
                image = icons[i];
            }
            String t = titles[i];
            addComponent(item = new EntrySelectionItem(w, h,
                    t,
                    image,
                    imgCheckBoxOn,
                    imageCheckBoxOff,
                    medFont, fontColour, this, i));
            item.setEventListener(this);
            item.setIsChecked(i == selectedIndex);
        }
    }
}
