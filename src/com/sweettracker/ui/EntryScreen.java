package com.sweettracker.ui;

import com.sweettracker.SweetTrackerController;
import com.sweettracker.model.Constants;
import com.sweettracker.model.Settings;
import com.sweettracker.model.User;
import com.sweettracker.model.Date;
import com.sweettracker.model.DiabetesTypeItem;
import com.sweettracker.model.Entries;
import com.sweettracker.model.Entry;
import com.sweettracker.model.visitors.DateVisitor;
import com.sweettracker.ui.components.EntryItem;
import com.sweettracker.ui.components.LevelEntryItem;
import com.sweettracker.utils.Database;
import com.sweettracker.utils.GlobalResources;
import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;

import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.ITouchEventListener;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.layout.BoxLayout;
import com.uikit.painters.BgColorPainter;
import com.uikit.painters.BorderPainter;
import com.uikit.utils.UikitConstant;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class EntryScreen extends SweetTrackerScreen {

    private int vgap;
    private Date date;
    private Image[] icons;
    private BitmapFont font_descript, font_guide;
    private int font_guide_color, font_desc_color, line_seperator_color;
    private int normal_bg_color, high_bg_color, critical_bg_color;
    private Settings settings;
    private User user;
    private Entry entry;
    private LevelEntryItem entryLevel;
    private EntryItem entryCal, entryTime, entryNote;
    public static final int INPUT_GLUCOSE_LEVEL = 0x330;
    public static final int INPUT_GLUCOSE_NOTE = 0x331;
    private BgColorPainter levelBgPainter;

    public EntryScreen(Date date) {
        this.date = date;
        init();
    }

    private void init() {
        initResources();
        initComponents();
    }

    private void initResources() {
        icons = new Image[]{
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_CAL_ICON),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_TIME_ICON),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_PEN_ICON)
        };

        normal_bg_color = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_NORMAL_COLOR));
        high_bg_color = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_HIGH_COLOR));
        critical_bg_color = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_CRITICAL_COLOR));

        line_seperator_color = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_LINE_SEPERATOR_COLOR));

        font_desc_color = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_TITLE_TEXT_COLOR));
        Image imgFontDesc = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_MEDIUM);
        font_descript = new BitmapFont(imgFontDesc, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_MEDIUM, 0);

        font_guide_color = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_DESC_TEXT_COLOR));
        Image imgFontGuide = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_SMALL);
        font_guide = new BitmapFont(imgFontGuide, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_SMALL, 0);
        padding = 4 * UiKitDisplay.getWidth() / 100;

        vgap = 2 * UiKitDisplay.getHeight() / 100;

        try {
            settings = (Settings) Database.getInstance().retrieveISerializable(Database.SETTINGS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            user = (User) Database.getInstance().retrieveISerializable(Database.USER);
            Entries entries = user.getEntries();
            if (entries != null) {
                DateVisitor d = new DateVisitor(this.date);
                entries.accept(d);
                entry = d.getEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (entry == null) {
            entry = new Entry(date, settings.getGlucoseUnit());
            entry.setLevelRange(Utils.getLevelRange(entry.getTimeInterval(), entry.getGlucoseLevel(), entry.getUnits(), DiabetesTypeItem.getDefault(settings.getDiabetesTypeItem())));
            user.addEntry(entry);
        }
    }

    private void setBg() {
        int levelRange = entry.getLevelRange();
        switch (levelRange) {
            case Constants.LEVEL_NORMAL: {
                levelBgPainter.setBgColor(normal_bg_color);
                break;
            }
            case Constants.LEVEL_HIGH: {
                levelBgPainter.setBgColor(high_bg_color);
                break;
            }
            case Constants.LEVEL_CRITICAL: {
                levelBgPainter.setBgColor(critical_bg_color);
                break;
            }
        }
    }

    private String getEntryTimeLevelDesc() {
        String entryTimeDesc = null;
        switch (entry.getTimeInterval()) {
            case Constants.TIME_LESS_2_HOURS: {
                entryTimeDesc = Resources.getInstance().getText(GlobalResources.TXT_LEVEL_LESS_2_HOURS);
                break;
            }
            case Constants.TIME_BEFORE_MEAL: {
                entryTimeDesc = Resources.getInstance().getText(GlobalResources.TXT_LEVEL_BEFORE_MEAL);
                break;
            }
        }
        return entryTimeDesc;
    }

    private String getEntryNote() {
        if (entry.getNote() == null || entry.getNote().trim().equals("")) {
            return Resources.getInstance().getText(GlobalResources.TXT_TAP_TO_ADD_NOTE);
        } else {
            return entry.getNote();
        }
    }

    private String getEntryDate() {
        return Utils.getFormattedDate(entry.getDate());
    }

    private float getEntryLevel() {
        float level = entry.getGlucoseLevel();
        if (level != 0.0f) {
            if (entry.getUnits() != settings.getGlucoseUnit()) {
                level = Utils.convertLevel(entry.getUnits(), settings.getGlucoseUnit(), level);
                level = Utils.get1DecimalPlace(level);
            }
        }
        return level;
    }

    private String getUnit() {
        return settings.getGlucoseUnit() == Constants.UNIT_MG ? ("(" + Constants.UNIT_MG_STR + ")") : ("(" + Constants.UNIT_MMOL_STR + ")");
    }

    private void initComponents() {
        setLayout(new BoxLayout(UikitConstant.VERTICAL, vgap));

        entryLevel = new LevelEntryItem(getWidth() - (vgap * 2), (int) (iHeight / 3.0), getEntryLevel(), getUnit(), Resources.getInstance().getText(GlobalResources.TXT_TAP_TO_EDIT), font_descript, font_guide, font_desc_color, font_guide_color, this);
        entryLevel.getStyle(true).setPadding(padding);
        BorderPainter borderPainter = new BorderPainter();
        borderPainter.setBorderColor(font_desc_color);
        borderPainter.setBorderSize(1);
        entryLevel.getStyle().addRenderer(levelBgPainter = new BgColorPainter(normal_bg_color));
        entryLevel.getStyle().addRenderer(borderPainter);
        addComponent(entryLevel);

        entryCal = new EntryItem(getWidth(), icons[0], getEntryDate(), null, font_descript, font_guide, font_desc_color, font_guide_color, line_seperator_color, this);
        addComponent(entryCal);

        entryTime = new EntryItem(getWidth(), icons[1], getEntryTimeLevelDesc(), Resources.getInstance().getText(GlobalResources.TXT_TAP_TO_EDIT), font_descript, font_guide, font_desc_color, font_guide_color, line_seperator_color, this);
        addComponent(entryTime);

        entryNote = new EntryItem(getWidth(), icons[2], getEntryNote(), Resources.getInstance().getText(GlobalResources.TXT_TAP_TO_EDIT), font_descript, font_guide, font_desc_color, font_guide_color, line_seperator_color, this);
        addComponent(entryNote);

        setBg();
        updateOffsets();
        getStyle(true).setPadding(topPadding + vgap, 0, bottomPadding + vgap, 0);

    }

    public void onEnter() {
        entryLevel.shakeIconImage();
    }

    public void setGlucoseLevel(float newLevel) {
        if (entry.getGlucoseLevel() != newLevel) {
            entry.setGlucoseLevel(newLevel);
            entry.setLevelRange(Utils.getLevelRange(entry.getTimeInterval(), newLevel, entry.getUnits(), DiabetesTypeItem.getDefault(settings.getDiabetesTypeItem())));

            // Visual update
            entryLevel.setLevel(newLevel);
            entryLevel.shakeIconImage();
            setBg();
        }
    }

    public void setNote(String note) {
        if (!note.equals(entry.getNote())) {
            entry.setNote(note);
            removeComponent(entryNote);
            entryNote = new EntryItem(getWidth(), icons[2], getEntryNote(), Resources.getInstance().getText(GlobalResources.TXT_TAP_TO_EDIT), font_descript, font_guide, font_desc_color, font_guide_color, line_seperator_color, this);
            addComponent(entryNote);
            entryNote.shakeIconImage();
            setIsScrollable(true);
            updateOffsets();
            getStyle(true).setPadding(topPadding + vgap, 0, bottomPadding + vgap, 0);
        }
    }

    public void onComponentEvent(Component c, int e, Object o, int p) {
        if (c == entryLevel) {
            if (e == ITouchEventListener.SINGLE_PRESS) {
                ((SweetTrackerController) controller).showGlucoseLevelDialog();
            }
        } else if (c == entryTime) {
            if (e == ITouchEventListener.SINGLE_PRESS) {
                int newTimeInterval = entry.getNextTimeInterval();
                if (entry.getTimeInterval() != newTimeInterval) {
                    entry.setTimeInterval(newTimeInterval);
                    entry.setLevelRange(Utils.getLevelRange(entry.getTimeInterval(), entry.getGlucoseLevel(), entry.getUnits(), DiabetesTypeItem.getDefault(settings.getDiabetesTypeItem())));

                    entryTime.setDescription(getEntryTimeLevelDesc());
                    entryTime.shakeIconImage();
                    setBg();
                }
            }
        } else if (c == entryNote) {
            if (e == ITouchEventListener.SINGLE_PRESS) {
                ((SweetTrackerController) controller).showGlucoseLevelNoteDialog(entry.getNote());
            }
        }
    }

    public void saveEntry() {
        try {
            Database.getInstance().saveISerializable(user, Database.USER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
