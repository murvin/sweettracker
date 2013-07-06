package com.sweettracker.ui;

import com.sweettracker.model.Constants;
import com.sweettracker.model.DiabetesTypeItem;
import com.sweettracker.model.Entries;
import com.sweettracker.model.Entry;
import com.sweettracker.model.Settings;
import com.sweettracker.model.User;
import com.sweettracker.model.visitors.MonthEntryVisitor;
import com.sweettracker.ui.components.Chart;
import com.sweettracker.ui.components.MonthSelector;
import com.sweettracker.utils.Database;
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
    private int axisColour;
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
        this.colours = new int[]{
            Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_NORMAL_COLOR)),
            Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_HIGH_COLOR)),
            Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_CRITICAL_COLOR)),
            Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_TEXT_COLOR))
        };

        Image imgFontSmall = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_SMALL);
        valueFont = new BitmapFont(imgFontSmall, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_SMALL, 0);

        yAxisLabel = "mmol";
        xAxisLabel = "days";

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
    }

    private void initChart() {
        int chartWidth = iWidth;
        int chartHeight = 225;

        int monthLength = Utils.getMonthLength(year, mnth);
        float targetLevel = settings.getTargetLevel();

        float[] levels = null;
        int[] colourCodes = null;
        int[] days = null;
        float maxLevel = 0.0f;
        float minLevel = 0.0f;

        if (entries != null) {
            try {
                MonthEntryVisitor v = new MonthEntryVisitor(mnth, year);
                entries.accept(v);
                Vector monthEntries = v.getEntries();
                int monthEntriesSize = monthEntries.size();
                if (!monthEntries.isEmpty()) {
                    levels = new float[monthEntriesSize];
                    colourCodes = new int[monthEntriesSize];
                    days = new int[monthEntriesSize];
                    for (int i = 0; i < monthEntriesSize; i++) {
                        Entry e = (Entry) monthEntries.elementAt(i);
                        if (i == 0) {
                            minLevel = e.getGlucoseLevel();
                            maxLevel = e.getGlucoseLevel();
                        } else {
                            minLevel = Math.min(minLevel, e.getGlucoseLevel());
                            maxLevel = Math.max(maxLevel, e.getGlucoseLevel());
                        }

                        levels[i] = e.getGlucoseLevel();
                        int levelRange = Utils.getLevelRange(e.getTimeInterval(), e.getGlucoseLevel(), e.getUnits(), DiabetesTypeItem.getDefault(settings.getDiabetesTypeItem()));
                        colourCodes[i] = (levelRange == Constants.LEVEL_NORMAL
                                ? this.colours[0] : (levelRange == Constants.LEVEL_HIGH ? this.colours[1] : this.colours[2]));
                        days[i] = e.getDate().getDay();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        chart = new Chart(chartWidth, chartHeight, levels, colourCodes, days, targetLevel, monthLength, axisColour, valueFont, xAxisLabel, yAxisLabel, maxLevel, minLevel, Utils.getMonthLength(year, mnth), this);
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
