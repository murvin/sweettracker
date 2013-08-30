package com.sweettracker.ui.components;

import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;
import com.uikit.motion.IMotionListener;
import com.uikit.motion.MotionEaseOutExpo;
import com.uikit.motion.Motion;
import com.uikit.painters.PatchPainter;
import com.uikit.utils.UikitConstant;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.ITouchEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.coreElements.BitmapFont;

import com.uikit.layout.GridLayout;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class CalMonth extends Panel implements IMotionListener, ITouchEventListener {

    private int calBorderColour;
    private BitmapFont font;
    private int padding;
    private int cellDimension;
    /** The calendar day represented by this component */
    private int day;
    /** Most fertile days */
    private int[] normalDays;
    /** Period days */
    private int[] criticalDays;
    private int[] highDays;
    private int patchBorder;
    private PatchPainter patchPainter;
    private Image imgHighlight;
    private int dayStrColour;
    private MotionEaseOutExpo mfx_slide;
    public static final int EXIT_FINISHED = 0x1101;
    public static final int ENTER_FINISHED = 0x1102;
    private int startOffset;
    private int monthLength;

    public CalMonth(int width, int height, int day, int monthLength,
            int[] normalDays,
            int[] criticalDays,
            int[] highDays, IComponentEventListener cel, Image imgHighLight, int starOffset) {
        super(width, height);
        this.cel = cel;
        this.day = day;
        this.normalDays = normalDays;
        this.criticalDays = criticalDays;
        this.highDays = highDays;
        this.imgHighlight = imgHighLight;
        this.startOffset = starOffset;
        this.monthLength = monthLength;
        initResources();

        setLayout(new GridLayout(6, 7, cellDimension, cellDimension, UikitConstant.HCENTER | UikitConstant.VCENTER));

        int topPadding = 0;
        getStyle(true).setPadding(topPadding, padding, 0, padding);

        initComponents(42);

    }

    private void initResources() {
        this.calBorderColour = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_CAL_BORDER_COLOR));
        this.dayStrColour = 0;
        
        Image imgFont = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_SMALL);
        font = new BitmapFont(imgFont, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_SMALL, 0);


        padding = 6 * UiKitDisplay.getWidth() / 100;
        cellDimension = (iWidth - (padding * 2)) / 7;
        this.patchBorder = 5;
        this.patchPainter = new PatchPainter(imgHighlight, patchBorder, patchBorder, patchBorder, patchBorder);
    }

    private void initComponents(int count) {
        for (int i = 1; i <= count; i++) {
            CalDay calDay = new CalDay(cellDimension, cellDimension, (i - startOffset) > monthLength ? -1 : (i > startOffset ? (i - startOffset) : -1), this.patchPainter);
            calDay.setEventListener(this.cel);
            calDay.setHasLeftBorder(true);
            calDay.setHasTopBorder(i <= 28);
            calDay.setHasRightBorder((i % 7 == 0) ? true : i == count);
            calDay.setHasBotBorder(i > 21);
            calDay.setBorderColour(calBorderColour);
            calDay.setFont(font, this.dayStrColour);

            if (normalDays != null && i > startOffset && (i - startOffset) <= monthLength) {
                for (int j = 0; j < normalDays.length; j++) {
                    int k = normalDays[j];
                    if (i - startOffset == k) {
                        calDay.setHasFillColour(true);
                        calDay.setFillColour(Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_NORMAL_COLOR)));
                    }
                }
            }

            if (criticalDays != null && i > startOffset && (i - startOffset) <= monthLength) {
                for (int j = 0; j < criticalDays.length; j++) {
                    int k = criticalDays[j];
                    if (i - startOffset == k) {
                        calDay.setHasFillColour(true);
                        calDay.setFillColour(Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_CRITICAL_COLOR)));
                    }
                }
            }

            if ((i - startOffset) == this.day && this.day != -1) {
                calDay.setHasFillColour(true);
                calDay.setFillColour(Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_TODAY_COLOR)));
            }


            if (highDays != null && i > startOffset && (i - startOffset) <= monthLength) {
                for (int j = 0; j < highDays.length; j++) {
                    int k = highDays[j];
                    if (i - startOffset == k) {
                        calDay.setHasFillColour(true);
                        calDay.setFillColour(Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_HIGH_COLOR)));
                    }
                }
            }

            addComponent(calDay);
        }
    }

    public void exit() {
        mfx_slide = new MotionEaseOutExpo(1, 4);
        mfx_slide.init(x, y, -UiKitDisplay.getWidth(), y, 15);
        mfx_slide.setMotionFXListener(this);
        this.motionFX = mfx_slide;
    }

    public void exitToRight() {
        mfx_slide = new MotionEaseOutExpo(1, 4);
        mfx_slide.init(x, y, UiKitDisplay.getWidth(), y, 15);
        mfx_slide.setMotionFXListener(this);
        this.motionFX = mfx_slide;
    }

    public void enter() {
        mfx_slide = new MotionEaseOutExpo(1, 4);
        mfx_slide.init(x, y, 0, y, 15);
        mfx_slide.setMotionFXListener(this);
        this.motionFX = mfx_slide;
    }

    public void onMotionStarted(Motion mfx) {
    }

    public void onMotionProgressed(Motion mfx, int progress) {
    }

    public void onMotionFinished(Motion mfx) {
        if (x != 0) {
            if (cel != null) {
                cel.onComponentEvent(this, EXIT_FINISHED, null, -1);
            }
        } else {
            if (cel != null) {
                cel.onComponentEvent(this, ENTER_FINISHED, null, -1);
            }
        }
    }

    public boolean onPress(int type, int iX, int iY) {
        return true;
    }

    public boolean onDrag(int type, int iStartX, int iStartY, int iDeltaX, int iDeltaY) {
        return false;
    }
}