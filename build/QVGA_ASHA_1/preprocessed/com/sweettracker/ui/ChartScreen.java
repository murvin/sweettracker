package com.sweettracker.ui;

import com.sweettracker.model.Constants;
import com.sweettracker.model.DiabetesTypeItem;
import com.sweettracker.model.Entries;
import com.sweettracker.model.Entry;
import com.sweettracker.model.Settings;
import com.sweettracker.model.User;
import com.sweettracker.model.visitors.MonthEntryVisitor;
import com.sweettracker.ui.components.Chart;
import com.sweettracker.ui.components.ChartKey;
import com.sweettracker.ui.components.MonthSelector;
import com.sweettracker.utils.Database;
import com.sweettracker.utils.GlobalResources;
import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;

import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.ITouchEventListener;
import com.uikit.coreElements.UiKitDisplay;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class ChartScreen extends SweetTrackerScreen {

    private MonthSelector monthSelector;
    private int mnth;
    private int year;
    private int axisColour, textColour;
    private int[] colours;
    private String yAxisLabel, xAxisLabel;
    private BitmapFont valueFont;
    private Chart chart;
    private Settings settings;
    private User user;
    private Entries entries;

    public ChartScreen() {
        setIsScrollable(true);
        initResources();
        initComponents();
    }

    private void initResources() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        mnth = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        axisColour = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_CAL_BORDER_COLOR));
        textColour = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_DESC_TEXT_COLOR));
        this.colours = new int[]{
            Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_NORMAL_COLOR)),
            Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_HIGH_COLOR)),
            Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_CRITICAL_COLOR)),
            Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_TEXT_COLOR))
        };

        Image imgFontSmall = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_SMALL);
        valueFont = new BitmapFont(imgFontSmall, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_SMALL, 0);

        padding = 4 * UiKitDisplay.getWidth() / 100;

        try {
            settings = (Settings) Database.getInstance().retrieveISerializable(Database.SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try { 
            user = (User) Database.getInstance().retrieveISerializable(Database.USER);
            entries = user.getEntries();
        } catch (Exception e) {
            e.printStackTrace();
        }

        yAxisLabel = "(" + (settings.getGlucoseUnit() == Constants.UNIT_MMOL ? Constants.UNIT_MMOL_STR : Constants.UNIT_MG_STR) + ")";
        xAxisLabel = "(" + Resources.getInstance().getText(GlobalResources.TXT_DAYS) + ")";
    }
    
    private double getConvertedTargetLevel() {
        double level = settings.getTargetLevel();
        if (level != 0.0f) {
            if (settings.getTargetGlucoseUnit() != settings.getGlucoseUnit()) {
                level = Utils.convertLevel(settings.getTargetGlucoseUnit(), settings.getGlucoseUnit(), level);
                level = Utils.get1DecimalPlace(level);
            }
        }
        return level;
    }

    private void initChart() {
        int chartWidth = iWidth;
        int chartHeight = 225;

        int monthLength = Utils.getMonthLength(year, mnth);
        double targetLevel = getConvertedTargetLevel();

        double[] levels = null;
        int[] colourCodes = null;
        int[] days = null;
        double maxLevel = 0.0f;
        double minLevel = 0.0f;
        double offset = settings.getGlucoseUnit() == Constants.UNIT_MMOL ? 0.5f : 10.0f;
        
        if (entries != null) {
            try {
                MonthEntryVisitor v = new MonthEntryVisitor(mnth, year);
                entries.accept(v);
                Vector monthEntries = v.getEntries();
                int monthEntriesSize = monthEntries.size();
                if (!monthEntries.isEmpty()) {
                    // Initialize array sizes
                    levels = new double[monthEntriesSize];
                    colourCodes = new int[monthEntriesSize];
                    days = new int[monthEntriesSize];
                    
                    for (int i = 0; i < monthEntriesSize; i++) {
                        Entry e = (Entry) monthEntries.elementAt(i);
                        int glucoseUnit = e.getUnits();
                        
                        if (i == 0) {
                            minLevel = Utils.convertLevel(glucoseUnit, settings.getGlucoseUnit(), e.getGlucoseLevel());
                            maxLevel = Utils.convertLevel(glucoseUnit, settings.getGlucoseUnit(), e.getGlucoseLevel());
                        } else {
                            minLevel = Math.min(minLevel, Utils.convertLevel(glucoseUnit, settings.getGlucoseUnit(), e.getGlucoseLevel()));
                            maxLevel = Math.max(maxLevel, Utils.convertLevel(glucoseUnit, settings.getGlucoseUnit(), e.getGlucoseLevel()));
                        }

                        levels[i] = Utils.convertLevel(glucoseUnit, settings.getGlucoseUnit(), e.getGlucoseLevel());
                        int levelRange = e.getLevelRange();
                        colourCodes[i] = (levelRange == Constants.LEVEL_NORMAL
                                ? this.colours[0] : (levelRange == Constants.LEVEL_HIGH ? this.colours[1] : this.colours[2]));
                        days[i] = e.getDate().getDay();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        chart = new Chart(chartWidth, chartHeight, levels, colourCodes, days, targetLevel, monthLength, axisColour, textColour, valueFont, xAxisLabel, yAxisLabel, maxLevel, minLevel, Utils.getMonthLength(year, mnth), this, offset);
    }

    private void initComponents() {
        this.monthSelector = new MonthSelector(iWidth, 9 * UiKitDisplay.getHeight() / 100, false);
        this.monthSelector.setCurrent(mnth, year);
        this.monthSelector.setEventListener(this);
        addComponent(monthSelector);

        initChart();
        this.chart.setEventListener(this);
        addComponent(this.chart);
        this.chart.x = UiKitDisplay.getWidth();
        int topOffset = Resources.getInstance().getThemeImage(GraphicsResources.IMG_BAR_BG).getHeight();
        this.chart.y += topOffset;
        this.chart.enter();
        
        ChartKey chartKey = new ChartKey(iWidth, 20, "* : " + Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_TARGET_TITLE), textColour, valueFont);
        addComponent(chartKey);

        updateOffsets();
        getStyle(true).setPadding(topPadding, 0, bottomPadding, 0);
    }

    private void enterChart(boolean isNext) {
        Chart temp = chart;
        initChart();
        chart.x = isNext ? UiKitDisplay.getWidth() : -UiKitDisplay.getWidth();
        chart.y = temp.y;
        chart.isLayoutable = false;
        chart.setEventListener(this);
        insertComponentAt(chart, 1, false);

        if (isNext) {
            temp.exit();
        } else {
            temp.exitToRight();
        }

        chart.enter();
    }

    public void onComponentEvent(Component c, int e, Object o, int p) {
        super.onComponentEvent(c, e, o, p);
        if (c == monthSelector) {
            mnth = e;
            year = p;
            enterChart(((Boolean) o).booleanValue());
        } else if (c instanceof Chart) {
            if (e == Chart.EXIT_FINISHED) {
                c.isLayoutable = false;
                removeComponent(c, false);
            } else if (e == Chart.ENTER_FINISHED) {
                chart.isLayoutable = true;
                chart.setEventListener(this);
            }
        }
    }

    public boolean onDrag(int type, int iStartX, int iStartY, int iDeltaX, int iDeltaY) {
        boolean isHandled = super.onDrag(type, iStartX, iStartY, iDeltaX, iDeltaY);
        if (type == ITouchEventListener.DRAG_RELEASE) {
            if (iDeltaX < 0) {
                monthSelector.next();
            } else if (iDeltaX > 0) {
                monthSelector.previous();
            }
        }

        return isHandled;
    }
}
