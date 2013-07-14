package com.sweettracker.ui.components;

import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;
import com.uikit.animations.Line;
import com.uikit.animations.UikitImageBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.ITouchEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.layout.BoxLayout;
import com.uikit.painters.PatchPainter;
import com.uikit.utils.ImageUtil;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class MenuItem extends Panel implements ITouchEventListener {

    private int layout_gap, layout_padding;
    private BitmapFont font;
    private Image imgText, imgHighlight;
    private int title_text_colour, desc_text_colour;
    private int id;
    private PatchPainter highLightPainter;
    public static final int EVENT_CLICK = 0xf001;

    public MenuItem(Image image, String label, int id) {
        super(0, 0);
        this.id = id;
        init(image, label);
    }

    private void initResources() {
        //#if QVGA
        layout_gap = 3;
        //#else
//#         layout_gap = 5;
        //#endif
        layout_padding = 3;

        title_text_colour = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_TITLE_TEXT_COLOR));
        desc_text_colour = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_DESC_TEXT_COLOR));
        imgHighlight = Resources.getInstance().getThemeImage(GraphicsResources.IMG_HIGH_BG);

        Image imgFont = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_MEDIUM);
        font = new BitmapFont(imgFont, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_MEDIUM, 0);
    }

    private void init(Image image, String label) {
        initResources();

        getStyle(true).setPadding(layout_padding, layout_padding * 3, layout_padding, layout_padding * 3);

        BoxLayout l = new BoxLayout(UikitConstant.VERTICAL, layout_gap);
        setLayout(l);
        addComponent(new UikitImageBox(image));
        expandToFitContent();

        addComponent(new Line(iWidth, desc_text_colour));


        imgText = font.drawStringToImage(label);
        imgText = ImageUtil.replaceColor(imgText, title_text_colour);
        addComponent(new UikitImageBox(imgText));


        expandToFitContent();
        int patchMargin = 3;
        highLightPainter = new PatchPainter(imgHighlight, patchMargin, patchMargin, patchMargin, patchMargin);
    }

    private void focus(boolean isOnFocus) {
        if (isOnFocus) {
            getStyle(true).addRenderer(highLightPainter);
        } else {
            if (getStyle(true).getRenderers() != null) {
                getStyle(true).getRenderers().removeElement(highLightPainter);
            }
        }
    }

    private void updateListener() {
        if (cel != null) {
            cel.onComponentEvent(this, EVENT_CLICK, null, id);
        }
    }

    public boolean onPress(int type, int x, int y) {
        if (type == ITouchEventListener.SINGLE_PRESS) {
            focus(false);
            updateListener();
            return true;
        } else if (type == ITouchEventListener.TOUCH_DOWN) {
            focus(true);
        } else if (type == ITouchEventListener.TOUCH_RELEASE) {
            focus(false);
        } else if (type == ITouchEventListener.LONG_PRESS) {
            focus(false);
        } else {
            focus(false);
        }
        return false;
    }

    public boolean onDrag(int type, int startX, int startY, int deltaX, int deltaY) {
        focus(false);
        return false;
    }
}
