package com.sweettracker.ui;

import com.sweettracker.model.Constants;
import com.sweettracker.model.Entries;
import com.sweettracker.model.Entry;
import com.sweettracker.model.Settings;
import com.sweettracker.model.User;
import com.sweettracker.model.visitors.EntryLevelVisitor;
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
    private int axisColour, targetColour;
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
        targetColour = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_TEXT_COLOR));

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

        float[] normal = null;
        float[] critical = null;
        float[] high = null;
        if (entries != null) {
            EntryLevelVisitor v = new EntryLevelVisitor(year, mnth, Constants.LEVEL_NORMAL);
            try {
                entries.accept(v);
                Vector normalEntries = v.getEntries();
                if (normalEntries != null) {
                    normal = new float[normalEntries.size()];
                    for (int i = 0; i < normalEntries.size(); i++) {
                        Entry e = (Entry) normalEntries.elementAt(i);
                        normal[i] = e.getGlucoseLevel();
                    }
                }

                v.setLevelRange(Constants.LEVEL_HIGH);
                entries.accept(v);
                Vector highEntries = v.getEntries();
                if (highEntries != null) {
                    high = new float[highEntries.size()];
                    for (int i = 0; i < highEntries.size(); i++) {
                        Entry e = (Entry) highEntries.elementAt(i);
                        high[i] = e.getGlucoseLevel();
                    }
                }

                v.setLevelRange(Constants.LEVEL_CRITICAL);
                entries.accept(v);
                Vector criticalEntries = v.getEntries();
                if (criticalEntries != null) {
                    critical = new float[criticalEntries.size()];
                    for (int i = 0; i < criticalEntries.size(); i++) {
                        Entry e = (Entry) criticalEntries.elementAt(i);
                        critical[i] = e.getGlucoseLevel();
                    }
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        chart = new Chart(chartWidth, chartHeight, normal, high, critical, targetLevel, monthLength, axisColour, targetColour, valueFont, this);
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
