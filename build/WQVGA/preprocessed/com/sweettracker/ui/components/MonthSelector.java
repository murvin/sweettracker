package com.sweettracker.ui.components;

import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;
import com.uikit.animations.UikitTextBox;
import com.uikit.utils.UikitConstant;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.SmartPanel;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.coreElements.BitmapFont;

import com.uikit.layout.BoxLayout;
import com.uikit.styles.TextStyle;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class MonthSelector extends SmartPanel implements IComponentEventListener {

    private Image imgLeftArrow, imgRightArrow;
    private MonthSelectorArrow leftArrow, rightArrow;
    /**
     * Shows the current month
     */
    private UikitTextBox txtMonth;
    /**
     * Layout padding
     */
    private int padding;
    private int currentMonth, currentYear;
    /**
     * Holds arrows and month string label
     */
    private Panel pnlNavigation;
    /**
     * Holds all days of the week
     */
    private Panel pnlDays;
    private boolean hasDays;
    private int fontColour;

    public MonthSelector(int width, int height, boolean hasDays) {
        super(width, height);
        this.hasDays = hasDays;
        initResources();

        BoxLayout l = (BoxLayout) getLayout();
        l.setGap(2 * UiKitDisplay.getHeight() / 100);
        getStyle(true).setPadding(0, padding, 0, padding);

        initComponents();
        setIsScrollable(false);
    }

    public void setCurrent(int currentMonth, int currentYear) {
        this.currentMonth = currentMonth;
        this.currentYear = currentYear;
        txtMonth.setText(Utils.getMonthsText()[currentMonth] + " " + currentYear);
    }

    public int getCurrentMonth() {
        return currentMonth % Utils.getMonthsText().length;
    }

    public void next() {
        txtMonth.setText(Utils.getMonthsText()[++currentMonth % Utils.getMonthsText().length] + " " + (currentMonth % Utils.getMonthsText().length == 0 ? ++currentYear : currentYear));

        if (cel != null) {
            cel.onComponentEvent(this, getCurrentMonth(), new Boolean(true), currentYear);
        }
    }

    public void previous() {
        if (currentMonth == 0) {
            currentMonth = Utils.getMonthsText().length;
        }

        txtMonth.setText(Utils.getMonthsText()[--currentMonth % Utils.getMonthsText().length] + " " + (currentMonth % Utils.getMonthsText().length == 11 ? --currentYear : currentYear));
        if (cel != null) {
            cel.onComponentEvent(this, getCurrentMonth(), new Boolean(false), currentYear);
        }
    }

    private void initResources() {
        imgLeftArrow = Resources.getInstance().getThemeImage(GraphicsResources.LEFT_ARROW);
        imgRightArrow = Resources.getInstance().getThemeImage(GraphicsResources.RIGHT_ARROW);

        padding = 6 * UiKitDisplay.getWidth() / 100;
    }

    private void initComponents() {

        pnlNavigation = new Panel(iWidth - (padding * 2), 10);
        pnlNavigation.setLayout(new BoxLayout(UikitConstant.HORIZONTAL, 0));
        addComponent(pnlNavigation);

        leftArrow = new MonthSelectorArrow(imgLeftArrow);
        leftArrow.setEventListener(this);
        TextStyle txtSyle = new TextStyle();
        txtSyle.setAlign(UikitConstant.HCENTER);

        fontColour = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_CAL_HEADER_TEXT_COLOR));

        Image imgFont = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_MEDIUM);
        BitmapFont font = new BitmapFont(imgFont, Utils.FONT_CHARS, Font.STYLE_BOLD, Font.SIZE_MEDIUM, 0);

        txtSyle.setFont(font);
        txtSyle.setFontColour(fontColour);

        txtMonth = new UikitTextBox(iWidth - (padding * 2) - (leftArrow.getWidth() * 2), "", txtSyle);

        rightArrow = new MonthSelectorArrow(imgRightArrow);
        rightArrow.setEventListener(this);

        pnlNavigation.addComponent(leftArrow);
        pnlNavigation.addComponent(txtMonth);
        pnlNavigation.addComponent(rightArrow);

        pnlNavigation.expandToFitContent();

        if (hasDays) {
            pnlDays = new Panel(iWidth - (padding * 2), 0);
            pnlDays.setLayout(new BoxLayout(UikitConstant.HORIZONTAL, 0));
            TextStyle daysStyle = new TextStyle();
            daysStyle.setAlign(UikitConstant.HCENTER);
            Image imgFontSmall = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_SMALL);
            BitmapFont fontSmall = new BitmapFont(imgFontSmall, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_SMALL, 0);
            daysStyle.setFont(fontSmall);
            int w = (iWidth - (padding * 2)) / Utils.getDaysText().length;

            for (int i = 0; i < Utils.getDaysText().length; i++) {
                pnlDays.addComponent(new UikitTextBox(w, Utils.getDaysText()[i], daysStyle));
            }

            pnlDays.expandToFitContent();

            addComponent(pnlDays);
        } else {

            getStyle(true).setPadding(0, padding, padding / 2, padding);
            expandToFitContent();
        }
    }

    public void onComponentEvent(Component c, int iEventId, Object paramObj, int iParam) {
        if (c == leftArrow) {
            previous();
        } else if (c == rightArrow) {
            next();
        }
    }

    public boolean onPress(int type, int iX, int iY) {
        return true;
    }

    public boolean onDrag(int type, int iStartX, int iStartY, int iDeltaX, int iDeltaY) {
        return true;
    }
}
