package com.sweettracker.ui;

import com.sweettracker.model.Constants;
import com.sweettracker.model.Settings;
import com.sweettracker.model.User;
import com.sweettracker.model.Date;
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
import com.uikit.coreElements.Panel;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.layout.BoxLayout;
import com.uikit.painters.BgColorPainter;
import com.uikit.painters.BorderPainter;
import com.uikit.painters.PatchPainter;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class EntryScreen extends SweetTrackerScreen {

    private int vgap;
    private Date date;
    private Image[] icons;
    private Image imgNewEntryBg;
    private BitmapFont font_descript, font_guide;
    private int font_guide_color, font_desc_color, line_seperator_color;
    private int normal_bg_color, high_bg_color, critical_bg_color;
    private Settings settings;
    private User user;
    private Entry entry;
    private Panel container;
    private LevelEntryItem entryLevel;
    private EntryItem entryCal, entryTime, entryNote;

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

        imgNewEntryBg = Resources.getInstance().getThemeImage(GraphicsResources.IMG_HIGH_BG);
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
    }

    private void setContainerBg() {
        if (entry == null) {
            container.getStyle(true).addRenderer(new PatchPainter(imgNewEntryBg, 2, 2, 2, 2));
        } else {
            int levelRange = entry.getLevelRange();
            switch (levelRange) {
                case Constants.LEVEL_NORMAL: {
                    container.getStyle(true).addRenderer(new BgColorPainter(normal_bg_color));
                    break;
                }
                case Constants.LEVEL_HIGH: {
                    container.getStyle(true).addRenderer(new BgColorPainter(high_bg_color));
                    break;
                }
                case Constants.LEVEL_CRITICAL: {
                    container.getStyle(true).addRenderer(new BgColorPainter(critical_bg_color));
                    break;
                }
            }
            BorderPainter borderP = new BorderPainter();
            borderP.setBorderColor(line_seperator_color);
            borderP.setBorderSize(1);
            container.getStyle().addRenderer(borderP);
        }
    }

    private String getEntryTimeLevelDesc() {
        if (entry == null) {
            return Resources.getInstance().getText(GlobalResources.TXT_LEVEL_LESS_2_HOURS);
        } else {
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
    }

    private String getEntryNote() {
        if (entry == null) {
            return Resources.getInstance().getText(GlobalResources.TXT_TAP_TO_ADD_NOTE);
        } else {
            return entry.getNote();
        }
    }

    private String getEntryDate() {
        if (entry == null) {
            return Utils.getFormattedDate(date);
        } else {
            return Utils.getFormattedDate(entry.getDate());
        }
    }

    private float getEntryLevel() {
        float level = entry == null ? 0.0f : entry.getGlucoseLevel();
        if (entry != null && level != 0.0f) {
            if (entry.getUnits() != settings.getGlucoseUnit()) {
                level = Utils.convertLevel(entry.getUnits(), settings.getGlucoseUnit(), level);
            }
        }
        return level;
    }

    private String getUnit() {
        return settings.getGlucoseUnit() == Constants.UNIT_MG ? "(mg/dL)" : "(mmol/L)";
    }

    private void initComponents() {
        int topOffset = Resources.getInstance().getThemeImage(GraphicsResources.IMG_BAR_BG).getHeight();
        container = new Panel(iWidth - (2 * vgap), iHeight - topOffset - topOffset - (vgap * 2));

        setContainerBg();
        addComponent(container);
        container.setLayout(new BoxLayout(UikitConstant.VERTICAL, vgap));

        entryLevel = new LevelEntryItem(container.getWidth(), (int) (iHeight / 3.0), getEntryLevel(), getUnit(), Resources.getInstance().getText(GlobalResources.TXT_TAP_TO_EDIT), font_descript, font_guide, font_desc_color, font_guide_color, this);
        container.addComponent(entryLevel);

        entryCal = new EntryItem(container.getWidth(), icons[0], getEntryDate(), Resources.getInstance().getText(GlobalResources.TXT_SWIPE_TO_EDIT), font_descript, font_guide, font_desc_color, font_guide_color, line_seperator_color, this);
        container.addComponent(entryCal);

        entryTime = new EntryItem(container.getWidth(), icons[1], getEntryTimeLevelDesc(), Resources.getInstance().getText(GlobalResources.TXT_SWIPE_TO_EDIT), font_descript, font_guide, font_desc_color, font_guide_color, line_seperator_color, this);
        container.addComponent(entryTime);

        entryNote = new EntryItem(container.getWidth(), icons[2], getEntryNote(), Resources.getInstance().getText(GlobalResources.TXT_TAP_TO_EDIT), font_descript, font_guide, font_desc_color, font_guide_color, line_seperator_color, this);
        container.addComponent(entryNote);

        updateOffsets();
        getStyle(true).setPadding(topPadding + vgap, 0, bottomPadding + vgap, 0);

        if (entry == null) {
            entry = new Entry(date, settings.getGlucoseUnit());
            user.addEntry(entry);
        }
    }

    public void onEnter() {
        entryLevel.shakeIconImage();
    }

    public void onComponentEvent(Component c, int e, Object o, int p) {
        if (c == entryLevel) {
            if (e == ITouchEventListener.SINGLE_PRESS) {
                float newLevel = 10.2f;
                if (entry.getGlucoseLevel() != newLevel) {
                    entry.setGlucoseLevel(newLevel);
                    entryLevel.setLevel(newLevel);
                    entryLevel.shakeIconImage();
                    setContainerBg();
                }
            }
        } else if (c == entryTime) {
            if (e == ITouchEventListener.DRAG_RELEASE) {
                int newTimeInterval;
                if (p == EntryItem.NEXT_SWIPE) {
                    newTimeInterval = entry.getNextTimeInterval();
                } else {
                    newTimeInterval = entry.getPreviousTimeInterval();
                }
                if (entry.getTimeInterval() != newTimeInterval) {
                    entry.setTimeInterval(newTimeInterval);
                    entryTime.setDescription(getEntryTimeLevelDesc());
                    entryTime.shakeIconImage();
                }
            }
        } else if (c == entryCal) {
            if (e == ITouchEventListener.DRAG_RELEASE) {
                Date newDate;
                if (p == EntryItem.NEXT_SWIPE) {
                    newDate = Utils.getNextDate(entry.getDate());
                } else {
                    newDate = Utils.getPreviousDate(entry.getDate());
                }
                entry.setDate(newDate);
                entryCal.setDescription(Utils.getFormattedDate(newDate));
                entryCal.shakeIconImage();
            }
        } else if (c == entryNote) {
            if (e == ITouchEventListener.SINGLE_PRESS) {
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
