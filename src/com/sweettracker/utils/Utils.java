package com.sweettracker.utils;

import com.sweettracker.model.Constants;
import com.sweettracker.model.DiabetesTypeItem;
import com.uikit.animations.AlertDialog;
import com.uikit.animations.UikitButton;
import com.uikit.animations.UikitTextInput;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.UikitFont;
import com.uikit.painters.BgImagePainter;
import com.uikit.painters.PatchPainter;
import com.uikit.styles.ComponentStyle;
import com.uikit.styles.TextStyle;
import com.uikit.utils.ImageUtil;
import com.uikit.utils.UikitConstant;
import java.util.Calendar;
import java.util.Date;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class Utils {

    public static final int ENTRY_LOCALES = 0X009;
    public static final int ENTRY_THEMES = 0x010;
    public static final String FONT_CHARS = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~¡£©«®°´»¿ÀÁÂÃÄÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝßàáâãäçèéêëìíîïñòóôõöùúûüýÿ™€";

    private Utils() {
    }

    public static String getEntryText(int entryId, int entryIndex) {
        StringBuffer text = new StringBuffer();
        switch (entryId) {
            case ENTRY_LOCALES: {
                if (entryIndex == 0) {
                    text.append("it-IT");
                } else if (entryIndex == 1) {
                    text.append("fr-FR");
                } else if (entryIndex == 2) {
                    text.append("es-ES");
                } else if (entryIndex == 3) {
                    text.append("en-GB");
                } else if (entryIndex == 4) {
                    text.append("de-DE");
                }
                break;
            }
            case ENTRY_THEMES: {
                if (entryIndex == 0) {
                    text.append("themeDefault");
                } else if (entryIndex == 1) {
                    text.append("themeFemale");
                } 
                break;
            }
        }
        return text.toString();
    }

    public static Image replaceColor(Image img, int iReplacementArgb) {
        if (img == null) {
            throw new IllegalArgumentException();
        }

        int iWidth = img.getWidth();
        int iHeight = img.getHeight();

        int[] argb = new int[iWidth * iHeight];

        img.getRGB(argb, 0, iWidth, 0, 0, iWidth, iHeight);

        for (int i = 0; i < argb.length; i++) {
            argb[i] = (0x00ffffff & iReplacementArgb) | (argb[i] & 0xff000000);
        }
        Image img2 = Image.createRGBImage(argb, iWidth, iHeight, true);
        argb = null;
        return img2;
    }

    public static ComponentStyle getDialogComponentStyle() {
        ComponentStyle containerStyle = new ComponentStyle();
        containerStyle.setPadding(20);
        int colour = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_LIGHTBOX_COLOR));
        Image bg = ImageUtil.generateTransparentImage(60, 60, (byte) 70, colour);
        containerStyle.addRenderer(new BgImagePainter(bg, UikitConstant.REPEAT));
        return containerStyle;
    }

    public static void applyTextStyles(AlertDialog dialog, int title_color, int desc_color) {

        Image imgFont = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_MEDIUM);
        BitmapFont medFont = new BitmapFont(imgFont, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_MEDIUM, 0);

        TextStyle txtStyle = new TextStyle(medFont);
        txtStyle.setFontColour(title_color);
        txtStyle.setAlign(UikitConstant.HCENTER);

        Image imgFontDesc = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_SMALL);
        BitmapFont descFont = new BitmapFont(imgFontDesc, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_SMALL, 0);

        TextStyle txtStyleDesc = new TextStyle(descFont);
        txtStyleDesc.setFontColour(desc_color);
        txtStyleDesc.setAlign(UikitConstant.HCENTER);


        dialog.setTitleTextStyle(txtStyle);
        dialog.setStyle(AlertDialog.COMP_TEXT, txtStyleDesc);
    }

    public static UikitButton getButton(String label, int width) {
        ComponentStyle[] buttonStyles;
        ComponentStyle style_onfocus = new ComponentStyle();

        Image imgOnTextInputFocus = Resources.getInstance().getThemeImage(GraphicsResources.IMG_HIGH_BG);
        int pBorder = 5;

        PatchPainter patchRenderer = new PatchPainter(imgOnTextInputFocus, pBorder, pBorder, pBorder, pBorder);
        style_onfocus.addRenderer(patchRenderer);

        Image imgButtonPatch = Resources.getInstance().getThemeImage(GraphicsResources.IMG_BUTTON_PATCH);
        ComponentStyle style = new ComponentStyle();
        pBorder = 5;
        style.addRenderer(new PatchPainter(imgButtonPatch, pBorder, pBorder, pBorder, pBorder));
        ComponentStyle styleOnFocus = new ComponentStyle();
        styleOnFocus.addRenderer(patchRenderer);
        buttonStyles = new ComponentStyle[]{style, styleOnFocus};
        UikitButton button = new UikitButton(0, 0, width, imgButtonPatch.getHeight(), label, 0);
        button.setStyle(UikitButton.COMP_SELF, UikitButton.STATE_ENABLED, buttonStyles[0]);
        button.setStyle(UikitButton.COMP_SELF, UikitButton.STATE_PRESSED, buttonStyles[1]);
        button.setStyle(UikitButton.COMP_SELF, UikitButton.STATE_FOCUSED, buttonStyles[1]);
        Image imgFont = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_MEDIUM);


        BitmapFont medFont = new BitmapFont(imgFont, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_MEDIUM, 0);

        button.setIsAutoResize(false);

        TextStyle st = new TextStyle(medFont);
        st.setFontColour(Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_BUTTON_TEXT_COLOR)));
        st.setAlign(UikitConstant.HCENTER);
        button.setStyle(UikitButton.COMP_TEXT, UikitButton.STATE_ENABLED, st);
        button.setStyle(UikitButton.COMP_TEXT, UikitButton.STATE_PRESSED, st);
        button.setStyle(UikitButton.COMP_TEXT, UikitButton.STATE_FOCUSED, st);
        return button;
    }

    public static void applyTextFieldStyles(UikitTextInput bti, UikitFont titleFont) {

        Image imgOnTextInputFocus = Resources.getInstance().getThemeImage(GraphicsResources.IMG_HIGH_BG);
        Image imgTextInputEnabled = Resources.getInstance().getThemeImage(GraphicsResources.IMG_BUTTON_PATCH);

        ComponentStyle[] styles = new ComponentStyle[2];
        ComponentStyle style_enabled = new ComponentStyle();
        ComponentStyle style_onfocus = new ComponentStyle();

        int pBorder = 10;

        PatchPainter patchRendererEnabled = new PatchPainter(imgTextInputEnabled, pBorder, pBorder, pBorder, pBorder);
        style_enabled.addRenderer(patchRendererEnabled);


        PatchPainter patchRenderer = new PatchPainter(imgOnTextInputFocus, pBorder, pBorder, pBorder, pBorder);
        style_onfocus.addRenderer(patchRendererEnabled);
        style_onfocus.addRenderer(patchRenderer);

        styles[0] = style_enabled;
        styles[1] = style_onfocus;

        bti.setStyle(UikitTextInput.COMP_SELF, UikitTextInput.STATE_ENABLED, styles[0]);
        bti.setStyle(UikitTextInput.COMP_SELF, UikitTextInput.STATE_FOCUSED, styles[1]);
        bti.setStyle(UikitTextInput.COMP_TEXTBOXTEXT, UikitTextInput.STATE_ENABLED, new TextStyle(titleFont));

    }

    public static String[] getMonthsText() {
        return new String[]{
            Resources.getInstance().getText(GlobalResources.TXT_JAN), Resources.getInstance().getText(GlobalResources.TXT_FEB),
            Resources.getInstance().getText(GlobalResources.TXT_MAR), Resources.getInstance().getText(GlobalResources.TXT_APR),
            Resources.getInstance().getText(GlobalResources.TXT_MAY), Resources.getInstance().getText(GlobalResources.TXT_JUN),
            Resources.getInstance().getText(GlobalResources.TXT_JULY), Resources.getInstance().getText(GlobalResources.TXT_AUG),
            Resources.getInstance().getText(GlobalResources.TXT_SEP), Resources.getInstance().getText(GlobalResources.TXT_OCT),
            Resources.getInstance().getText(GlobalResources.TXT_NOV), Resources.getInstance().getText(GlobalResources.TXT_DEC)
        };

    }

    public static String[] getDaysText() {
        return new String[]{
            Resources.getInstance().getText(GlobalResources.TXT_MON), Resources.getInstance().getText(GlobalResources.TXT_TUE),
            Resources.getInstance().getText(GlobalResources.TXT_WED), Resources.getInstance().getText(GlobalResources.TXT_THURS),
            Resources.getInstance().getText(GlobalResources.TXT_FRI),
            Resources.getInstance().getText(GlobalResources.TXT_SAT),
            Resources.getInstance().getText(GlobalResources.TXT_SUN)
        };
    }

    public static int getMonthLength(int year, int month) {
        switch (month) {
            case 0:
                return 31;
            case 1:
                if (year % 400 == 0) {
                    return 29;
                } else if (year % 100 == 0) {
                    return 28;
                } else if (year % 4 == 0) {
                    return 29;
                } else {
                    return 28;
                }
            case 2:
                return 31;
            case 3:
                return 30;
            case 4:
                return 31;
            case 5:
                return 30;
            case 6:
                return 31;
            case 7:
                return 31;
            case 8:
                return 30;
            case 9:
                return 31;
            case 10:
                return 30;
            case 11:
                return 31;
            default:
                return 31;
        }
    }

    public static int getZellerDay(int year, int month, int day) {
        month += 1;
        if (month < 3) {
            month += 12;
            year -= 1;
        }


        int k = year % 100;
        int j = year / 100;
        int d = ((day + (((month + 1) * 26) / 10) + k + (k / 4) + (j / 4))
                + (5 * j)) % 7;
        return d;
    }

    public static com.sweettracker.model.Date getCurrentDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int mnth = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR);
        int mins = c.get(Calendar.MINUTE);
        int am_pm = c.get(Calendar.AM_PM);
        return new com.sweettracker.model.Date(day, mnth, year, hour, mins, am_pm);
    }

    public static String getFormattedDate(com.sweettracker.model.Date date) {
        StringBuffer s = new StringBuffer();
        s.append(getMonthsText()[date.getMonth()]);
        s.append(" ").append(date.getDay()).append(", ").append(date.getYear());
        s.append(", ").append(date.getHour()).append(":").append(date.getMins());
        s.append(" ").append(date.getAmPm() == Calendar.AM ? "AM" : "PM");
        return s.toString();
    }

    public static float convertLevel(int currentUnit, int targetUnit, float level) {
        if (currentUnit == Constants.UNIT_MG) {
            level *= 0.0555f;
        } else if (currentUnit == Constants.UNIT_MMOL) {
            level *= 18.0182;
        }
        return level;
    }

    public static com.sweettracker.model.Date getNextDate(com.sweettracker.model.Date currDate) {
        int day = currDate.getDay();
        int month = currDate.getMonth();
        int year = currDate.getYear();

        int monthLength = getMonthLength(year, month);
        if (++day > monthLength) {
            month++;
            day = 1;
            if (month > 11) {
                month = 0;
                year++;
            }
        }
        return new com.sweettracker.model.Date(day, month, year, currDate.getHour(), currDate.getMins(), currDate.getAmPm());
    }

    public static com.sweettracker.model.Date getPreviousDate(com.sweettracker.model.Date currDate) {
        int day = currDate.getDay();
        int month = currDate.getMonth();
        int year = currDate.getYear();
        if (--day < 1) {
            month--;
            day = getMonthLength(year, month);
            if (month < 0) {
                month = 12;
                year--;
            }
        }
        return new com.sweettracker.model.Date(day, month, year, currDate.getHour(), currDate.getMins(), currDate.getAmPm());
    }

    public static String[] getFormattedDiabetesType(DiabetesTypeItem item) {
        int type = item.getType();
        String title = null;
        String beforeMealRange;
        String afterMealRange;
        switch (type) {
            case Constants.DIABETES_TYPE_NONE: {
                title = Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_DIABETES_TYPE_NONE);
                break;
            }
            case Constants.DIABETES_TYPE_ONE: {
                title = Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_DIABETES_TYPE_ONE);
                break;
            }
            case Constants.DIABETES_TYPE_TWO: {
                title = Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_DIABETES_TYPE_TWO);
                break;
            }
        }

        beforeMealRange = "(" + item.getBeforeMealMin() + " - " + item.getBeforeMealMax() + ")";
        afterMealRange = "(" + item.getAfterMealMin() + " - " + item.getAfterMealMax() + ")";

        return new String[]{title, beforeMealRange, afterMealRange};
    }

    public static int getLevelRange(int timeInterval, float glucoseLevel, int units, DiabetesTypeItem item) {
        if (units != Constants.UNIT_MMOL) {
            glucoseLevel = Utils.convertLevel(units, Constants.UNIT_MMOL, glucoseLevel);
        }
        
        if (timeInterval == Constants.TIME_BEFORE_MEAL) {
            if (glucoseLevel < item.getBeforeMealMax()) {
                return Constants.LEVEL_NORMAL;
            } else if (glucoseLevel >= item.getBeforeMealMax() && glucoseLevel <= item.getBeforeMealMax() + 1) {
                return Constants.LEVEL_HIGH;
            } else if (glucoseLevel > item.getBeforeMealMax()) {
                return Constants.LEVEL_CRITICAL;
            }
        } else if (timeInterval == Constants.TIME_LESS_2_HOURS) {
            if (glucoseLevel < item.getAfterMealMax()) {
                return Constants.LEVEL_NORMAL;
            } else if (glucoseLevel >= item.getAfterMealMax() && glucoseLevel <= item.getAfterMealMax() + 1) {
                return Constants.LEVEL_HIGH;
            } else if (glucoseLevel > item.getAfterMealMax()) {
                return Constants.LEVEL_CRITICAL;
            }
        }
        return 0;
    }
}
