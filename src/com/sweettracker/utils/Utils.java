package com.sweettracker.utils;

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
                    text.append("en-GB");
                } else if (entryIndex == 1) {
                    text.append("fr-FR");
                } else if (entryIndex == 2) {
                    text.append("it-IT");
                } else if (entryIndex == 3) {
                    text.append("de-DE");
                } else if (entryIndex == 4) {
                    text.append("es-ES");
                }
                break;
            }
            case ENTRY_THEMES: {
                if (entryIndex == 0) {
                    text.append("themeDefault");
                } else if (entryIndex == 1) {
                    text.append("themeAutumn");
                } else if (entryIndex == 2) {
                    text.append("themeWinter");
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
        Image bg = ImageUtil.generateTransparentImage(60, 60, (byte) 60, colour);
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
}
