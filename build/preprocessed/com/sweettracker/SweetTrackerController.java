package com.sweettracker;

import com.sweettracker.model.Settings;
import com.sweettracker.model.User;
import com.sweettracker.ui.SplashScreen;
import com.sweettracker.utils.Database;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;
import com.uikit.mvc.patterns.Controller;
import com.uikit.mvc.patterns.Screen;
import java.io.IOException;
import javax.microedition.midlet.MIDlet;

public class SweetTrackerController extends Controller {

    // LAYER CONSTANTS //
    public static final int LAYER_SCREEN = 0x011;
    public static final int LAYER_DIALOG = 0x019;
    // SCREEN CONSTANTS //
    public static final int SCREEN_SPLASH = 0x010;

    public SweetTrackerController() {
        Settings settings = null;
        try {
            settings = (Settings) Database.getInstance().retrieveISerializable(Database.SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (settings == null) {
            settings = new Settings();
            settings.initDefault();
            try {
                Database.getInstance().saveISerializable(settings, Database.SETTINGS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        User user = null;
        try {
            user = (User) Database.getInstance().retrieveISerializable(Database.USER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user == null) {
            user = new User();
            try {
                Database.getInstance().saveISerializable(user, Database.USER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Resources.getInstance().initResources("GlobalResources", settings.getCurrentLocale());
            Resources.getInstance().initTheme("GraphicsResources", Utils.getEntryText(Utils.ENTRY_THEMES, settings.getCurrentTheme()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void init(MIDlet midlet) {
        super.init(midlet);
    }

    public void addScreen(Screen screen) {
        view.getLayer(LAYER_SCREEN).addComponent(screen);
        screen.enter();
    }

    public void removeScreen(Screen screen) {
        view.getLayer(LAYER_SCREEN).removeComponent(screen);
    }

    public void addLayers() {
        view.addLayer(LAYER_SCREEN);
        view.addLayer(LAYER_DIALOG);
    }

    public Screen loadScreen(int screen_id, Object param) {
        switch (screen_id) {
            case SCREEN_SPLASH: {
                return new SplashScreen();
            }
            default:
                throw new IllegalStateException();
        }
    }
}
