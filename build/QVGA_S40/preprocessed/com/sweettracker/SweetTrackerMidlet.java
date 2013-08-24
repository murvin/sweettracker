package com.sweettracker;

import javax.microedition.midlet.*;

public class SweetTrackerMidlet extends MIDlet {

    public void startApp() {
        SweetTrackerController controller = new SweetTrackerController();
        controller.init(this);
        controller.navigateScreen(SweetTrackerController.SCREEN_SPLASH, false, new Boolean(true));
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
