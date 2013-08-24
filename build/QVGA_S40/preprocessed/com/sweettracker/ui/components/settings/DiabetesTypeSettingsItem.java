package com.sweettracker.ui.components.settings;

import com.sweettracker.model.DiabetesTypeItem;
import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;
import com.uikit.animations.UikitImageBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.ITouchEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.layout.BoxLayout;
import com.uikit.painters.BorderPainter;
import com.uikit.painters.PatchPainter;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Image;

public class DiabetesTypeSettingsItem extends IndicatorSettingsItem {

    private Panel container;

    public DiabetesTypeSettingsItem(int w, String title, String desc, BitmapFont title_font, BitmapFont desc_font, int title_color, int desc_color, Object param, IComponentEventListener cel) {
        super(w, title, desc, title_font, desc_font, title_color, desc_color, param, cel);
    }

    public void addContent() {
        container = new Panel(iWidth, 0);
        container.setLayout(new BoxLayout(UikitConstant.HORIZONTAL, 3));
        DiabetesTypeItem[] items = (DiabetesTypeItem[]) param;
        for (int i = 0; i < items.length; i++) {
            DiabetesTypeItem d = items[i];
            String[] details = Utils.getFormattedDiabetesType(d);
            DiabetesItem item = new DiabetesItem((iWidth / 3) - 4, details[0], details[1], details[2], i < items.length - 1);
            item.setEventListener(this);
            container.addComponent(item);
        }

        container.expandToFitContent();
        addComponent(container);
    }

    public void updateItemLevels( DiabetesTypeItem[] items) {
        container.removeAllComponents();
        for (int i = 0; i < items.length; i++) {
            DiabetesTypeItem d = items[i];
            String[] details = Utils.getFormattedDiabetesType(d);
            DiabetesItem item = new DiabetesItem((iWidth / 3) - 4, details[0], details[1], details[2], i < items.length - 1);
            item.setEventListener(this);
            container.addComponent(item);
        }
        container.expandToFitContent();
    }

    public void moveIndicatorToIndx(int compIndex) {
        Component c = this.container.componentAt(compIndex);
        super.moveIndicator(c.x + (c.getWidth() / 2));
        super.currentSelIdx = compIndex;
    }

    class DiabetesItem extends Panel implements ITouchEventListener {

        private int id;
        private PatchPainter highLightPainter;

        public DiabetesItem(int iWidth, String title, String desc, String desc2, boolean addRightBorder) {
            super(iWidth, 0);
            Image imgHighlight = Resources.getInstance().getThemeImage(GraphicsResources.IMG_HIGH_BG);
            int patchMargin = 3;
            int padding = 5;
            highLightPainter = new PatchPainter(imgHighlight, patchMargin, patchMargin, patchMargin, patchMargin);
            setLayout(new BoxLayout(UikitConstant.VERTICAL, 3));
            Image imgText = desc_font.drawStringToImage(title);
            imgText = Utils.replaceColor(imgText, desc_color);
            addComponent(new UikitImageBox(imgText));

            Image imgDesc = desc_font.drawStringToImage(desc);
            imgDesc = Utils.replaceColor(imgDesc, desc_color);
            addComponent(new UikitImageBox(imgDesc));

            Image imgDesc2 = desc_font.drawStringToImage(desc2);
            imgDesc2 = Utils.replaceColor(imgDesc2, desc_color);
            addComponent(new UikitImageBox(imgDesc2));
            expandToFitContent();

            if (addRightBorder) {
                BorderPainter border = new BorderPainter();
                border.setBorderColor(desc_color);
                border.setBorderSize(0, 1, 0, 0);
                getStyle(true).addRenderer(border);
            }
            getStyle(true).setPadding(padding, 0, padding, 0);
        }

        private void focus(boolean isOnFocus) {
            if (isOnFocus) {
                getStyle(true).addRenderer(highLightPainter);
            } else {
                if (getStyle(true).getRenderers() != null) {
                    getStyle().getRenderers().removeElement(highLightPainter);
                }
            }
        }

        private void updateListener() {
            if (cel != null) {
                cel.onComponentEvent(this, 1, null, id);
            }
        }

        public boolean onPress(int type, int x, int y) {
            if (type == ITouchEventListener.SINGLE_PRESS) {
                focus(false);
                updateListener();
            } else if (type == ITouchEventListener.TOUCH_DOWN) {
                focus(true);
            } else if (type == ITouchEventListener.TOUCH_RELEASE) {
                focus(false);
            } else if (type == ITouchEventListener.LONG_PRESS) {
                focus(false);
            } else {
                focus(false);
            }
            return true;
        }

        public boolean onDrag(int type, int startX, int startY, int deltaX, int deltaY) {
            focus(false);
            return true;
        }
    }
}
