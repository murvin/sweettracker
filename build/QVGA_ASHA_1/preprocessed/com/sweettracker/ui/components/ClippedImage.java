package com.sweettracker.ui.components;

import com.uikit.coreElements.Component;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ClippedImage extends Component {

    private Image image;
    private int xClip;
    private final int DELTA_X = 5;
    private final int DELTA_DAMPER = 2;
    private int counter;
    private boolean animate;
    public static final int EVENT_ANIMATION_FINISH = 0x001;

    public ClippedImage(Image image) {
        super(image.getWidth(), image.getHeight());
        this.image = image;
    }

    public void reveal() {
        xClip = 0;
        animate = true;
    }

    protected boolean animate() {
        if (animate) {
            counter++;

            if (counter % DELTA_DAMPER == 0) {
                xClip += DELTA_X;
            }

            if (xClip >= iWidth) {
                animate = false;
                if (cel != null) {
                    cel.onComponentEvent(this, EVENT_ANIMATION_FINISH, null, 0);
                }
            }
        }

        return super.animate();
    }

    protected void drawCurrentFrame(Graphics g) {
        super.drawCurrentFrame(g);
        int clipX = g.getClipX();
        int clipY = g.getClipY();
        int clipW = g.getClipWidth();
        int clipH = g.getClipHeight();

        g.clipRect(clipX, clipY, xClip, clipH);
        if (image != null) {
            g.drawImage(image, 0, 0, 20);
        }

        g.clipRect(clipX, clipY, clipW, clipH);

    }
}
