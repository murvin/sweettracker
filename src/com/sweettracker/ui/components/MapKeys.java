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
 
import com.uikit.coreElements.UikitFont;
import com.uikit.layout.GridLayout;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
 
public class MapKeys extends Panel {

    private int padding;
    private int cellWidth, cellHeight;
    private UikitFont font;
    private int txtLabelColour;
    private int gap;
    
    public MapKeys(int width, int height) {
        super(width, height);
        initResources();

        setLayout(new GridLayout(2, 2, cellWidth, cellHeight, UikitConstant.HCENTER | UikitConstant.VCENTER));

        getStyle(true).setPadding(padding / 4, padding, padding / 2, padding);

        initComponents();
    }

    private void initResources() {
        
        Image imgFont = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_SMALL);
        font = new BitmapFont(imgFont, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_SMALL, 0);

        padding = 6 * UiKitDisplay.getWidth() / 100;
        cellWidth = (iWidth - (padding * 2)) / 2;
        cellHeight = iHeight / 2;
        gap = 2 * UiKitDisplay.getWidth() / 100;
        txtLabelColour = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_TEXT_COLOR));
    }

    private void initComponents() {
        MapKey normalKey = new MapKey(cellWidth, cellHeight, Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_NORMAL_COLOR)), txtLabelColour, Resources.getInstance().getText(GlobalResources.TXT_MAPKEY_NORMAL));
        setCommonAttrs(normalKey);
        addComponent(normalKey);

        MapKey today = new MapKey(cellWidth, cellHeight, Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_TODAY_COLOR)), txtLabelColour, Resources.getInstance().getText(GlobalResources.TXT_MAPKEY_TODAY));
        setCommonAttrs(today);
        addComponent(today);

        MapKey highKey = new MapKey(cellWidth, cellHeight, Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_HIGH_COLOR)), txtLabelColour, Resources.getInstance().getText(GlobalResources.TXT_MAPKEY_HIGH));
        setCommonAttrs(highKey);
        addComponent(highKey);

        MapKey criticalKey = new MapKey(cellWidth, cellHeight, Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_MAPKEY_CRITICAL_COLOR)), txtLabelColour, Resources.getInstance().getText(GlobalResources.TXT_MAPKEY_CRITICAL));
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
        private UikitFont font;
        /** Gap between text and box */
        private int gap;

        public MapKey(int width, int height, int colour, int textColour, String label) {
            super(width, height);
            this.colour = colour;
            this.textColour = textColour;
            this.label = label;
        }

        public void setFont(UikitFont font) {
            this.font = font;
        }

        public void setGap(int gap) {
            this.gap = gap;
        }

        protected void drawCurrentFrame(Graphics g) {
            g.setColor(colour);
            g.fillRect(gap, gap, iHeight - (gap * 2), iHeight - (gap * 2));
            g.setColor(textColour);
            if (font != null) {
                font.drawString(g, label, iHeight, (iHeight - font.getHeight()) / 2, 20);
            }
        }
    }
}
