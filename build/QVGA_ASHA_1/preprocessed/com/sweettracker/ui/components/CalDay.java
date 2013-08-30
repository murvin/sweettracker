package com.sweettracker.ui.components;

import com.sweettracker.utils.Utils;
import com.uikit.coreElements.BitmapFont;
import com.uikit.painters.BorderPainter;
import com.uikit.painters.PatchPainter;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.IFocusable;
import com.uikit.coreElements.ITouchEventListener;
import com.uikit.coreElements.IUikitInputHandler;
import com.uikit.coreElements.UiKitDisplay;
import javax.microedition.lcdui.Canvas;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class CalDay extends Component implements IFocusable, ITouchEventListener, IUikitInputHandler {

    private boolean isOnFocus;
    private int fillColour;
    private boolean hasTopBorder;
    private boolean hasLeftBorder;
    /**
     * Bottom drawn if true
     */
    private boolean hasBotBorder;
    /**
     * Right border drawn if true
     */
    private boolean hasRightBorder;
    /**
     * Day has background fill colour
     */
    private boolean hasFillColour;
    /**
     * Calendar day
     */
    private String day;
    int d;
    private BitmapFont font;
    private Image imgDay;
    private int dayStrColour;
    private boolean useBitmapFont;
    private final int BORDER_SIZE = 1;
    private final int FILL_GAP = 1;
    private PatchPainter patchPainter;

    public CalDay(int width, int height, int day, PatchPainter patchRenderer) {
        super(width, height);
        d = day;
        this.day = String.valueOf(day);
        this.patchPainter = patchRenderer;
    }

    public void onFocus() {
        isOnFocus = true;
    }

    public void onDefocus() {
        isOnFocus = false;
    }

    public boolean isFocused() {
        return isOnFocus;
    }

    private void updateListener() {
        if (cel != null) {
            cel.onComponentEvent(this, Integer.parseInt(day), day, -1);
        }
    }

    public boolean onKeyPressed(int iKeyCode) {
        int key = UiKitDisplay.getGameAction(iKeyCode);
        if (key == Canvas.FIRE) {
            updateListener();
            return true;
        }
        return false;
    }

    public boolean onKeyReleased(int iKeyCode) {
        return false;
    }

    public boolean onKeyRepeated(int iKeyCode) {
        return false;
    }

    public void setHasTopBorder(boolean hasTopBorder) {
        this.hasTopBorder = hasTopBorder;
    }

    public void setHasLeftBorder(boolean hasLeftBorder) {
        this.hasLeftBorder = hasLeftBorder;
    }

    public void setHasBotBorder(boolean hasBotBorder) {
        this.hasBotBorder = hasBotBorder;
    }

    public void setHasRightBorder(boolean hasRightBorder) {
        this.hasRightBorder = hasRightBorder;
    }

    public void setBorderColour(int borderColour) {
        BorderPainter border = new BorderPainter();
        border.setBorderColor(borderColour);
        border.setBorderSize(hasTopBorder ? BORDER_SIZE : 0, hasRightBorder ? BORDER_SIZE : 0, hasBotBorder ? BORDER_SIZE : 0, hasLeftBorder ? BORDER_SIZE : 0);

        getStyle(true).addRenderer(border);
    }

    public void setFillColour(int fillColour) {
        this.fillColour = fillColour;
    }

    public void setHasFillColour(boolean hasFillColour) {
        this.hasFillColour = hasFillColour;
    }

    public void setFont(BitmapFont font, int dayStrColour) {
        this.font = font;
        this.dayStrColour = dayStrColour;
        if (this.d != -1) {
            this.imgDay = this.font.drawStringToImage(this.day);
            this.imgDay = Utils.replaceColor(this.imgDay, dayStrColour);
        }
        this.useBitmapFont = true;
    }

    protected void drawCurrentFrame(Graphics g) {
        if (hasFillColour) {
            g.setColor(fillColour);
            g.fillRect(BORDER_SIZE + FILL_GAP, BORDER_SIZE + FILL_GAP,
                    iWidth - ((hasRightBorder ? BORDER_SIZE * 2 : BORDER_SIZE) + (FILL_GAP * 2)),
                    iHeight - ((hasBotBorder ? BORDER_SIZE * 2 : BORDER_SIZE) + (FILL_GAP * 2)));
        }

        if (useBitmapFont && imgDay != null) {
            g.drawImage(imgDay, (iWidth - imgDay.getWidth()) / 2, (iHeight - imgDay.getHeight()) / 2, 20);
        } else {
            g.setColor(dayStrColour);
            if (d != -1) {
                font.drawString(g, day, (iWidth - font.stringWidth(day)) / 2, (iHeight - font.getHeight()) / 2, 20);
            }
        }

        if (isOnFocus) {
            this.patchPainter.paint(this, g);
        }
    }

    public boolean onPress(int type, int iX, int iY) {
        if (type == ITouchEventListener.SINGLE_PRESS) {
            updateListener();
            return true;
        }
        return false;
    }

    public boolean onDrag(int type, int iStartX, int iStartY, int iDeltaX, int iDeltaY) {
        return true;
    }
}
