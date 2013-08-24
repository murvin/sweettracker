package com.sweettracker.ui.components;

import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Component;
import com.uikit.utils.ImageUtil;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ChartKey extends Component {

    private Image imglabel;

    public ChartKey(int iWidth, int iHeight, String label, int keyColour, BitmapFont font) {
        super(iWidth, iHeight);
        imglabel = font.drawStringToImage(label);
        imglabel = ImageUtil.replaceColor(imglabel, keyColour);
    }

    protected void drawCurrentFrame(Graphics g) {
        super.drawCurrentFrame(g);
        if (imglabel != null) {
            g.drawImage(imglabel, (iWidth - imglabel.getWidth()) / 2, 0, 20);
        }
    }
}
