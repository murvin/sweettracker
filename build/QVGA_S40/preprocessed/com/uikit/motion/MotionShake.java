
package com.uikit.motion;

public class MotionShake extends Motion {

    private int shakeOffsetX;

    private int shakeOffsetY;

    public MotionShake (int iMotionTickCount) {
        super(iMotionTickCount);
    }

    public void init (int startPosX, int startPosY, int nTotalStep, int shakeOffsetX, int shakeOffsetY) {
        super.init(startPosX, startPosY, startPosX, startPosY, nTotalStep);

        this.shakeOffsetX = shakeOffsetX;
        this.shakeOffsetY = shakeOffsetY;
    }
    
    protected void repeat (int iRepeatedCount) {
    }

    protected void move() {
        curPosX = startPosX + ( curStep % 2 == 0 ? shakeOffsetX : -shakeOffsetX );
        curPosY = startPosY + ( curStep % 2 == 0 ? shakeOffsetY : -shakeOffsetY );
    }
}
