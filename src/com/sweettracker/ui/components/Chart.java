package com.sweettracker.ui.components;

import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.ITouchEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.motion.IMotionListener;
import com.uikit.motion.Motion;
import com.uikit.motion.MotionEaseOutExpo;
import com.uikit.motion.MotionLinear;
import com.uikit.utils.ImageUtil;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Chart extends Panel implements IMotionListener, ITouchEventListener {

    private int[] colours, days;
    private float[] levels;
    private int axisColour, textColour;
    private float target;
    private MotionEaseOutExpo mfx_slide;
    private MotionLinear mfxCharts;
    private BitmapFont font;
    private String xAxisLabel, yAxisLabel;
    private Image imgXaxisLabel, imgYaxisLabel;
    private int startingOffset;
    private float maxLevel, minLevel;
    private Image maxLevelImg, minLevelImg, midMonthImg, targetIndicator;
    private int maxDays;
    public static final int EXIT_FINISHED = 0x1201;
    public static final int ENTER_FINISHED = 0x1202;
    private final int BAR_WIDTH = 5;
    private final int STEPS = 15;
    private ChartBar[] bars;

    public Chart(int iWidth, int iHeight, float[] levels, int[] colours, int[] days, float target, int xAxisLength,
            int axisColour, int textColour, BitmapFont font, String xAxisLabel, String yAxisLabel, float maxLevel, float minLevel, int maxDays, IComponentEventListener cel) {
        super(iWidth, iHeight);
        this.levels = levels;
        this.colours = colours;
        this.days = days;
        this.cel = cel;
        this.target = target;
        this.font = font;
        this.axisColour = axisColour;
        this.textColour = textColour;

        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.maxLevel = maxLevel;
        this.minLevel = minLevel;
        this.maxDays = maxDays;

        initResources();
    }

    private void initResources() {
        startingOffset = 15;
        imgXaxisLabel = font.drawStringToImage(xAxisLabel);
        imgXaxisLabel = ImageUtil.replaceColor(imgXaxisLabel, this.textColour);

        imgYaxisLabel = font.drawStringToImage(yAxisLabel);
        imgYaxisLabel = ImageUtil.replaceColor(imgYaxisLabel, this.textColour);

        if (target > maxLevel) {
            maxLevel = target + 0.5f;
        }

        if (target < minLevel) {
            minLevel = target - 0.5f;
        }

        if (minLevel != maxLevel) {
            target = (iHeight - (startingOffset * 2)) * (maxLevel - target) / (maxLevel - minLevel);
        }

        if (levels != null) {
            String maxLevelStr = String.valueOf(maxLevel);
            maxLevelImg = font.drawStringToImage(maxLevelStr);
            maxLevelImg = ImageUtil.replaceColor(maxLevelImg, this.textColour);

            String minLevelStr = String.valueOf(minLevel);
            minLevelImg = font.drawStringToImage(minLevelStr);
            minLevelImg = ImageUtil.replaceColor(minLevelImg, this.textColour);

            midMonthImg = font.drawStringToImage("" + maxDays / 2);
            midMonthImg = ImageUtil.replaceColor(midMonthImg, this.textColour);

            targetIndicator = font.drawStringToImage("*");
            targetIndicator = ImageUtil.replaceColor(targetIndicator, this.textColour);
        }
    }

    private void addBarCharts() {
        if (levels != null) {
            this.bars = new ChartBar[levels.length];
            for (int i = 0; i < levels.length; i++) {
                float level = levels[i];
                int colour = colours[i];
                int day = days[i];

                int height = (int) (level * (iHeight - (startingOffset * 2)) / maxLevel);
                int xCoor = (int) (day * (iWidth - (startingOffset * 2)) / maxDays) + (startingOffset / 2);
                xCoor += (BAR_WIDTH + 1) / 2;
                ChartBar bar = new ChartBar(BAR_WIDTH, height, axisColour, colour, STEPS);
                bar.x = xCoor;
                bar.y = iHeight - height - startingOffset;
                addComponent(bar);
                this.bars[i] = bar;
            }
            enterCharts();
        }
    }

    protected void drawCurrentFrame(Graphics g) {
        // Axes
        g.setColor(axisColour);
        g.drawLine(startingOffset, startingOffset, startingOffset, iHeight - (startingOffset / 2));
        g.drawLine(startingOffset / 2, iHeight - startingOffset, iWidth - startingOffset, iHeight - startingOffset);

        // Axes's labels
        g.drawImage(imgYaxisLabel, startingOffset / 2, 0, 20);
        g.drawImage(imgXaxisLabel, iWidth - imgXaxisLabel.getWidth() - startingOffset, iHeight - imgXaxisLabel.getHeight(), 20);

        if (minLevelImg != null) {
            // draw Y axis levels
            g.drawImage(minLevelImg, startingOffset - minLevelImg.getWidth() - 1, iHeight - startingOffset - minLevelImg.getHeight(), 20);
            g.drawImage(maxLevelImg, startingOffset - maxLevelImg.getWidth() - 1, startingOffset, 20);

            // draw X axis label
            g.drawImage(midMonthImg, iWidth / 2, iHeight - midMonthImg.getHeight(), 20);

            // draw target line
            g.setColor(textColour);
            g.drawLine(startingOffset / 2, (int) target, iWidth - startingOffset, (int) target);
            g.drawImage(targetIndicator, startingOffset - (targetIndicator.getWidth() + 1), (int) (target - targetIndicator.getHeight()), 20);
        }

        super.drawCurrentFrame(g);
    }

    public void exit() {
        mfx_slide = new MotionEaseOutExpo(1, 4);
        mfx_slide.init(x, y, -UiKitDisplay.getWidth(), y, 15);
        mfx_slide.setMotionFXListener(this);
        this.motionFX = mfx_slide;
    }

    public void exitToRight() {
        mfx_slide = new MotionEaseOutExpo(1, 4);
        mfx_slide.init(x, y, UiKitDisplay.getWidth(), y, 15);
        mfx_slide.setMotionFXListener(this);
        this.motionFX = mfx_slide;
    }

    public void enter() {
        mfx_slide = new MotionEaseOutExpo(1, 4);
        mfx_slide.init(x, y, startingOffset / 2, y, 15);
        mfx_slide.setMotionFXListener(this);
        this.motionFX = mfx_slide;
    }

    private void enterCharts() {
        mfxCharts = new MotionLinear(1, MotionLinear.MOFX_LINEAR_CSPEED_INERTIA);
        mfxCharts.init(0, 0, 0, (int) maxLevel, STEPS, 0.0f, 5.0f);
        mfxCharts.setMotionFXListener(new IMotionListener() {
            public void onMotionStarted(Motion motion) {
            }

            public void onMotionProgressed(Motion motion, int progress) {
                for (int i = 0; i < bars.length; i++) {
                    ChartBar b = bars[i];
                    b.step();
                }
            }

            public void onMotionFinished(Motion motion) {
                mfxCharts = null;
            }
        });
    }

    protected boolean animate() {
        if (mfxCharts != null) {
            mfxCharts.tick();
        }

        return super.animate(); //To change body of generated methods, choose Tools | Templates.
    }

    public void onMotionStarted(Motion mfx) {
    }

    public void onMotionProgressed(Motion mfx, int progress) {
    }

    public void onMotionFinished(Motion mfx) {
        if (x != startingOffset / 2) {
            if (cel != null) {
                cel.onComponentEvent(this, EXIT_FINISHED, null, -1);
            }
        } else {
            if (cel != null) {
                cel.onComponentEvent(this, ENTER_FINISHED, null, -1);
            }
            addBarCharts();
        }
    }

    public boolean onPress(int type, int iX, int iY) {
        return true;
    }

    public boolean onDrag(int type, int iStartX, int iStartY, int iDeltaX, int iDeltaY) {
        return false;
    }
}
