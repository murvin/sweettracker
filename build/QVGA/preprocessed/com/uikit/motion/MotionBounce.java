
package com.uikit.motion;


public class MotionBounce extends Motion {

    //constant for bounce calculation
    private final double i0 = 1.0d / 2.75d;

    private final double i1 = 2.0d / 2.75d;

    private final double i2 = 2.5d / 2.75d;

    private final double i3 = 1.5d / 2.75d;

    private final double i4 = 2.25d / 2.75d;

    private final double i5 = 2.625d / 2.75d;

    //variable for bounce calculation
    private double i6;

    private double i7;

    private double i8;

    public MotionBounce (int iMotionTickCount) {
        super(iMotionTickCount);
    }

    public void init (int startPosX, int startPosY, int endPosX, int endPosY, int nTotalStep) {
        super.init(startPosX, startPosY, endPosX, endPosY, nTotalStep);
    }

    protected void move () {
        curPosX = (int) mm0(curStep, startPosX, endPosX - startPosX, totalStep);
        curPosY = (int) mm0(curStep, startPosY, endPosY - startPosY, totalStep);
    }

    protected void repeat (int iRepeatedCount) {
        /* ignore */
    }

    private double mm0 (int iCurStep, int b, int c, int nTotalStep) {
        i6 = 1.000000d * iCurStep;
        i7 = 1.000000d * nTotalStep;
        i8 = i6 / i7;

        if ( i8 < i0 ) {
            return c * ( 7.5625d * i8 * i8 ) + b;
        }
        else if ( i8 < i1 ) {
            i8 -= i3;
            return c * ( 7.5625d * i8 * i8 + 0.75d ) + b;
        }
        else if ( i8 < i2 ) {
            i8 -= i4;
            return c * ( 7.5625d * i8 * i8 + 0.9375d ) + b;
        }
        else {
            i8 -= i5;
            return c * ( 7.5625d * i8 * i8 + 0.984375d ) + b;
        }
    }
}
