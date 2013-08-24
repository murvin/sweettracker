package com.sweettracker.ui.components;

import com.sweettracker.utils.Utils;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.ITouchEventListener;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class EntrySelectionItem extends Component implements ITouchEventListener {

    private boolean isChecked;
    private Image img_icon, img_chkbox_on, img_chkbox_off;
    private int x_icon, x_chkbox, y_icon, y_chkbox, y_font;
    private BitmapFont font;
    private int padding;
    public static final int EVENT_CHECKED = 0x001;
    public static final int EVENT_UNCHECKED = 0x002;
    private boolean isUnCheckable;
    private int index;
    private Image imgText;

    public EntrySelectionItem(int width, int height,
            String title,
            Image img_icon,
            Image chkBoxOn,
            Image chkBoxOff,
            BitmapFont font,
            int fontColour,
            IComponentEventListener listener,
            int index) {
        super(width, height);
        this.img_icon = img_icon;
        this.img_chkbox_on = chkBoxOn;
        this.img_chkbox_off = chkBoxOff;
        this.font = font;
        this.cel = listener;
        this.index = index;

        imgText = this.font.drawStringToImage(title);
        imgText = Utils.replaceColor(imgText, fontColour);

        padding = 2 * UiKitDisplay.getWidth() / 100;
        reCalcPos();
    }

    public void reCalcPos() {
        x_icon = img_icon == null ? 0 : (iWidth - img_icon.getWidth()) / 2;
        y_font = (iHeight - font.getHeight()) / 2;
        y_icon = img_icon == null ? y_font : (iHeight - img_icon.getHeight()) / 2;

        x_chkbox = iWidth - img_chkbox_on.getWidth() - (padding * 2);
        y_chkbox = (iHeight - img_chkbox_off.getHeight()) / 2;
    }

    protected void drawCurrentFrame(Graphics g) {
        if (imgText != null) {
            g.drawImage(imgText, padding * 2, y_font, 20);
        }

        if (img_icon != null) {
            g.drawImage(img_icon, x_icon, y_icon, 20);
        }

        if (isChecked) {
            g.drawImage(img_chkbox_on, x_chkbox, y_chkbox, 20);
        } else {
            g.drawImage(img_chkbox_off, x_chkbox, y_chkbox, 20);
        }
    }

    public void setIsUnCheckable(boolean isUnCheckable) {
        this.isUnCheckable = isUnCheckable;
    }

    private void updateListener() {
        if (cel != null) {
            cel.onComponentEvent(this, isChecked ? EVENT_CHECKED : EVENT_UNCHECKED, null, index);
        }
    }

    public boolean onPress(int type, int iX, int iY) {
        if (type == ITouchEventListener.SINGLE_PRESS) {
            if (!isChecked) {
                isChecked = !isChecked;
                updateListener();
                return true;
            } else {
                if (isUnCheckable) {
                    isChecked = !isChecked;
                    updateListener();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onDrag(int type, int iStartX, int iStartY, int iDeltaX, int iDeltaY) {
        return true;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return this.isChecked;
    }
}
