package com.sweettracker.ui.components;

import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Component;

import com.uikit.utils.ImageUtil;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class TermsDelimiter extends Component {

    private int lineColour;
    private Image imgText;

    public TermsDelimiter(String text, int width, int height, int lineColour, BitmapFont font, int fontColour) {
        super(width, height + 1);
        this.lineColour = lineColour;

        imgText = font.drawStringToImage(text);
        imgText = ImageUtil.replaceColor(imgText, fontColour);
    }

    protected void drawCurrentFrame(Graphics g) {
        if (imgText != null) {
            g.drawImage(imgText, 0, 0, 20);
        }
        g.setColor(lineColour);
        g.drawLine(0, iHeight, iWidth, iHeight);
    }
}
