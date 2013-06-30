package com.sweettracker.ui;

import com.sweettracker.SweetTrackerController;
import com.sweettracker.model.Constants;
import com.sweettracker.model.DiabetesTypeItem;
import com.sweettracker.model.Settings;
import com.sweettracker.ui.components.settings.DiabetesTypeSettingsItem;
import com.sweettracker.ui.components.settings.LanguageSettingsItem;
import com.sweettracker.ui.components.settings.PinCodeSettingsItem;
import com.sweettracker.ui.components.settings.TargetLevelSettingsItem;
import com.sweettracker.ui.components.settings.ThemeSettingsItem;
import com.sweettracker.ui.components.settings.UnitsSettingsItem;
import com.sweettracker.utils.*;
import com.uikit.animations.UikitButton;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.layout.BoxLayout;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class SettingsScreen extends SweetTrackerScreen {

    private Image[] flags;
    private String[] item_titles;
    private String[] item_desc;
    private BitmapFont font_title, font_desc;
    private int font_title_color, font_desc_color;
    private Settings settings;
    private int vgap;
    private DiabetesTypeSettingsItem diabetic_type_item;
    private LanguageSettingsItem language_item;
    private UnitsSettingsItem units_item;
    private PinCodeSettingsItem pincode_item;
    private TargetLevelSettingsItem targetLevelSettingsItem;
    private ThemeSettingsItem themeSettingsItem;
    public static final int INPUT_PIN = 0x010;
    public static final int INPUT_CONFIRM_PIN = 0x030;
    public static final int INPUT_TARGET = 0x020;

    public SettingsScreen() {
        init();
    }

    public void setSettingsPinCode(String code) {
        settings.setCode(code);
        pincode_item.setPinCode(code);
    }

    public void setTargetLevel(float targetLevel) {
        settings.setTargetLevel(targetLevel);
        targetLevelSettingsItem.setTarget("" + targetLevel);
    }

    private void init() {
        initResources();
        initComponents();
    }

    private void initResources() {
        flags = new Image[]{
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_FLAG_IT),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_FLAG_FR),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_FLAG_ES),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_FLAG_EN),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_FLAG_DE)
        };

        item_titles = new String[]{
            Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_LANGUAGE_TITLE),
            Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_DIABETES_TYPE_TITLE),
            Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_UNITS_TITLE),
            Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_PINCODE_TITLE),
            Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_TARGET_TITLE)
        };

        item_desc = new String[]{
            Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_LANGUAGE_DESC),
            Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_DIABETES_TYPE_DESC),
            Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_UNITS_DESC),
            Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_PINCODE_DESC),
            Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_TARGET_DESC)
        };


        font_title_color = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_TITLE_TEXT_COLOR));
        Image imgFontTitle = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_MEDIUM);
        font_title = new BitmapFont(imgFontTitle, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_MEDIUM, 0);

        font_desc_color = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_DESC_TEXT_COLOR));
        Image imgFontDesc = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_SMALL);
        font_desc = new BitmapFont(imgFontDesc, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_SMALL, 0);

        padding = 4 * UiKitDisplay.getWidth() / 100;

        vgap = 2 * UiKitDisplay.getHeight() / 100;

        try {
            settings = (Settings) Database.getInstance().retrieveISerializable(Database.SETTINGS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initComponents() {
        getStyle(true).setPadding(padding * 2, padding, padding, padding);
        setLayout(new BoxLayout(UikitConstant.VERTICAL, vgap * 2));
        int w = iWidth - (padding * 2);

        language_item = new LanguageSettingsItem(w, item_titles[0], item_desc[0], font_title, font_desc, font_title_color, font_desc_color, flags, this);
        addComponent(language_item);

        DiabetesTypeItem[] items = new DiabetesTypeItem[]{
            DiabetesTypeItem.getDefault(Constants.DIABETES_TYPE_NONE),
            DiabetesTypeItem.getDefault(Constants.DIABETES_TYPE_ONE),
            DiabetesTypeItem.getDefault(Constants.DIABETES_TYPE_TWO)};
        diabetic_type_item = new DiabetesTypeSettingsItem(w, item_titles[1], item_desc[1], font_title, font_desc, font_title_color, font_desc_color, items, cel);
        addComponent(diabetic_type_item);

        units_item = new UnitsSettingsItem(w, item_titles[2], item_desc[2], font_title, font_desc, font_title_color, font_desc_color, null, this);
        addComponent(units_item);

        pincode_item = new PinCodeSettingsItem(w, item_titles[3], item_desc[3], font_title, font_desc, font_title_color, font_desc_color, settings.getCode(), this);
        pincode_item.setEventListener(this);
        addComponent(pincode_item);

        targetLevelSettingsItem = new TargetLevelSettingsItem(w, item_titles[4], item_desc[4], font_title, font_desc, font_title_color, font_desc_color, new Float(settings.getTargetLevel()), this);
        targetLevelSettingsItem.setEventListener(this);
        addComponent(targetLevelSettingsItem);

        themeSettingsItem = new ThemeSettingsItem(w, Resources.getInstance().getText(GlobalResources.TXT_THEME_TITLE), Resources.getInstance().getText(GlobalResources.TXT_THEME_DESC), font_title, font_desc, font_title_color, font_desc_color, new Integer(settings.getCurrentTheme()), this);
        themeSettingsItem.setEventListener(this);
        addComponent(themeSettingsItem);

        updateOffsets();

        getStyle(true).setPadding(topPadding + vgap, 0, bottomPadding + (vgap * 3), 0);
    }

    private void updateSettings() {
        language_item.moveIndicatorToIndx(settings.getCurrentLocale());
        units_item.moveIndicatorToIndx(settings.getGlucoseUnit() == Constants.UNIT_MG ? 0 : 1);
        int type = settings.getDiabetesTypeItem();
        int idx = type == Constants.DIABETES_TYPE_NONE ? 0 : (type == Constants.DIABETES_TYPE_ONE ? 1 : 2);
        diabetic_type_item.moveIndicatorToIndx(idx);
        themeSettingsItem.moveIndicatorToIndx(settings.getCurrentTheme());
    }

    public void onComponentEvent(Component c, int e, Object o, int p) {
        super.onComponentEvent(c, e, o, p);
        if (c instanceof UikitButton) {
            if (e == UikitButton.EVENT_PRESSED) {
                if (p == PinCodeSettingsItem.BUTTON_ID) {
                    ((SweetTrackerController) controller).showPinCodeDialog();
                } else if (p == TargetLevelSettingsItem.BUTTON_ID) {
                    ((SweetTrackerController) controller).showTargetLevelDialog();
                }
            }
        }
    }

    public void onEnter() {
        updateSettings();
    }

    public boolean hasLocaleChanged() {
        return settings.getCurrentLocale() != language_item.getCurrentSelIdx();
    }

    public boolean hasThemeChanged() {
        return settings.getCurrentTheme() != themeSettingsItem.getCurrentSelIdx();
    }

    public Settings getCurrentSettings() {
        return this.settings;
    }

    public void saveSettings() {
        settings.setCurrentLocale(language_item.getCurrentSelIdx());
        settings.setGlucoseUnit(units_item.getCurrentSelIdx() == 0 ? Constants.UNIT_MG : Constants.UNIT_MMOL);
        int diabetes_idx = diabetic_type_item.getCurrentSelIdx();
        settings.setDiabetesType(diabetes_idx == 0 ? Constants.DIABETES_TYPE_NONE : (diabetes_idx == 1 ? Constants.DIABETES_TYPE_ONE : Constants.DIABETES_TYPE_TWO));
        settings.setCurrentTheme(themeSettingsItem.getCurrentSelIdx());
        try {
            Database.getInstance().saveISerializable(settings, Database.SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
