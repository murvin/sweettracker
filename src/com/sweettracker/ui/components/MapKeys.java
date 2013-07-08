package com.sweettracker.ui.components;

import com.sweettracker.utils.GlobalResources;
import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;
import com.uikit.utils.UikitConstant;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.Panel;
import com.uikit.coreElements.BitmapFont;

import com.uikit.layout.GridLayout;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class MapKeys extends Panel {

    private int padding;
    private int cellWidth, cellHeight;
    private BitmapFont font;
    private int txtLabelColour;
    private int gap;
    private boolean useBitmapFont;

    public MapKeys(int width, int height) {
        super(width, height);
        initResources();

        setLayout(new GridLayout(2, 2, cellWidth, cellHeight, UikitConstant.HCENTER | UikitConstant.VCENTER));

        getStyle(true).setPadding(padding / 4, padding, padding / 2, padding);

        initComponents();
    }

    private void initResources() {
        useBitmapFont = true;
        Image imgFont = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_SMALL);
        font = new BitmapFont(imgFont, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_SMALL, 0);

        padding = 6 * UiKitDisplay.getWidth() / 100;
        cellWidth = (iWidth - (padding * 2)) / 2;
        cellHeight = iHeight / 2;
        gap = 2 * UiKitDisplay.getWidth() / 100;
        txtLabelColour = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_TEXT_COLOR));
    }

    private void initComponents() {
        MapKey normalKey = new MapKey(cellWidth, cellHeight, Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_NORMAL_COLOR)), txtLabelColour, Resources.getInstance().getText(GlobalResources.TXT_MAPKEY_NORMAL), useBitmapFont);
        setCommonAttrs(normalKey);
        addComponent(normalKey);

        MapKey today = new MapKey(cellWidth, cellHeight, Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_TODAY_COLOR)), txtLabelColour, Resources.getInstance().getText(GlobalResources.TXT_MAPKEY_TODAY), useBitmapFont);
        setCommonAttrs(today);
        addComponent(today);

        MapKey highKey = new MapKey(cellWidth, cellHeight, Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_HIGH_COLOR)), txtLabelColour, Resources.getInstance().getText(GlobalResources.TXT_MAPKEY_HIGH), useBitmapFont);
        setCommonAttrs(highKey);
        addComponent(highKey);

        MapKey criticalKey = new MapKey(cellWidth, cellHeight, Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_CRITICAL_COLOR)), txtLabelColour, Resources.getInstance().getText(GlobalResources.TXT_MAPKEY_CRITICAL), useBitmapFont);
        setCommonAttrs(criticalKey);
        addComponent(criticalKey);
    }

    private void setCommonAttrs(MapKey key) {
        key.setFont(font);
        key.setGap(gap);
    }

    class MapKey extends Component {

        private int colour;
        private int textColour;
        private String label;
        private BitmapFont font;
        private Image img;
        /**
         * Gap between text and box
         */
        private int gap;
        private boolean useBitmapImage;

        public MapKey(int width, int height, int colour, int textColour, String label, boolean useBitmapImage) {
            super(width, height);
            this.colour = colour;
            this.textColour = textColour;
            this.label = label;
            this.useBitmapImage = useBitmapImage;
        }

        public void setFont(BitmapFont font) {
            this.font = font;
            this.img = this.font.drawStringToImage(label);
            this.img = Utils.replaceColor(this.img, textColour);
        }

        public void setGap(int gap) {
            this.gap = gap;
        }

        protected void drawCurrentFrame(Graphics g) {
            g.setColor(textColour);
            g.fillRect(gap - 1 , gap - 1, (iHeight - (gap * 2)) + 2, (iHeight - (gap * 2)) + 2);
            g.setColor(colour);
            g.fillRect(gap, gap, iHeight - (gap * 2), iHeight - (gap * 2));
            if (useBitmapImage) {
                g.drawImage(img, iHeight, (iHeight - img.getHeight()) / 2, 20);
            } else {
                g.setColor(textColour);
                if (font != null) {
                    font.drawString(g, label, iHeight, (iHeight - font.getHeight()) / 2, 20);
                }
            }
        }
    }
}
