package com.sweettracker;

import com.sweettracker.model.Settings;
import com.sweettracker.model.User;
import com.sweettracker.ui.HomeScreen;
import com.sweettracker.ui.SettingsScreen;
import com.sweettracker.ui.SplashScreen;
import com.sweettracker.ui.components.MenuBar;
import com.sweettracker.ui.components.NavigationBar;
import com.sweettracker.utils.*;
import com.uikit.animations.AlertDialog;
import com.uikit.animations.InputDialog;
import com.uikit.animations.UikitButton;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.TouchEventHandler;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.mvc.patterns.Controller;
import com.uikit.mvc.patterns.Screen;
import java.io.IOException;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;

public class SweetTrackerController extends Controller {

    // LAYER CONSTANTS //
    private final int LAYER_SCREEN = 0x011;
    private final int LAYER_DIALOG = 0x019;
    private final int LAYER_NAVIGATION = 0x020;
    // SCREEN CONSTANTS //
    public static final int SCREEN_SPLASH = 0x010;
    public static final int SCREEN_HOME = 0x011;
    public static final int SCREEN_SETTINGS = 0x012;
    // MENU CONSTANTS 
    public static final int MENU_EXIT = 0x101;
    public static final int MENU_BACK = 0x102;
    public static final int MENU_ABOUT = 0x103;
    public static final int MENU_SAVE = 0x104;
    public static final int MENU_CANCEL = 0x105;
    public static final int MENU_ACCEPT = 0x106;
    public static final int MENU_DECLINE = 0x107;
    //DIALOG CONSTANTS
    final int ALERT_DIALOG = 0x401;
    final int ALERT_DIALOG_YES = 0x402;
    final int ALERT_DIALOG_NO = 0x403;
    final int INPUT_DIALOG_OK = 0x404;
    final int INPUT_DIALG_CANCEL = 0x405;
    private NavigationBar topBar;
    private MenuBar menuBar;
    private TouchEventHandler currentTouchHandler;

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
        topBar = new NavigationBar(canvas.getWidth());
        view.getLayer(LAYER_NAVIGATION).addComponent(this.topBar);

        this.menuBar = new MenuBar();
        this.menuBar.setEventListener(this);
        menuBar.y = canvas.getHeight() - menuBar.getHeight();
        menuBar.x = -canvas.getWidth();
        view.getLayer(LAYER_NAVIGATION).addComponent(this.menuBar);
    }

    public void enter(Screen screen) {
        if (screen instanceof HomeScreen) {
            topBar.setLabel(Resources.getInstance().getText(GlobalResources.TXT_LABEL_HOME));
            menuBar.setRsk(Resources.getInstance().getText(GlobalResources.TXT_COMMON_EXIT), MENU_EXIT);
        } else if (screen instanceof SettingsScreen) {
            topBar.setLabel(Resources.getInstance().getText(GlobalResources.TXT_MENU_SETTINGS));
            menuBar.setRsk(Resources.getInstance().getText(GlobalResources.TXT_COMMON_BACK), MENU_BACK);
        }

        screen.enter();
    }

    public void addScreen(Screen screen) {
        if (screen instanceof SplashScreen) {
            screen.x = 0;
            screen.y = 0;
        } else {
            screen.y = topBar.getHeight();
            if (previous_screen_id == SCREEN_SETTINGS) {
                screen.x = -canvas.getWidth();
            } else {
                screen.x = canvas.getWidth();
            }
        }

        view.getLayer(LAYER_SCREEN).addComponent(screen);
    }

    public void exit(Screen screen) {
        if (screen instanceof HomeScreen || screen instanceof SplashScreen) {
            screen.exit();
        } else {
            screen.exitToRight();
        }
    }

    public void removeScreen(Screen screen) {
        view.getLayer(LAYER_SCREEN).removeComponent(screen);

    }

    public void addLayers() {
        view.addLayer(LAYER_SCREEN);
        view.addLayer(LAYER_NAVIGATION);
        view.addLayer(LAYER_DIALOG);
    }

    public Screen loadScreen(int screen_id, Object param) {
        switch (screen_id) {
            case SCREEN_SPLASH: {
                return new SplashScreen();
            }
            case SCREEN_HOME: {
                return new HomeScreen(canvas.getWidth(), canvas.getHeight() - topBar.getHeight() - menuBar.getHeight());
            }
            case SCREEN_SETTINGS: {
                return new SettingsScreen(canvas.getWidth(), canvas.getHeight() - topBar.getHeight() - menuBar.getHeight());
            }
            default:
                throw new IllegalStateException();
        }
    }

    public void onSplashFinish() {
        navigateScreen(SweetTrackerController.SCREEN_HOME, true, null);
        topBar.enter();
        menuBar.enter();
    }

    public void onComponentEvent(Component c, int eventId, Object o, int param) {
        super.onComponentEvent(c, eventId, o, param);
        if (c instanceof HomeScreen) {
            if (eventId == HomeScreen.MENU_CLICK) {
                if (param == 3) {
                    navigateScreen(SCREEN_SETTINGS, false, null);
                }
            }
        } else if (c instanceof MenuBar) {
            if (eventId == MENU_BACK) {
                navigateScreen(SCREEN_HOME, false, null);
            } else if (eventId == MENU_EXIT) {
                confirmExit();
            }
        } else if (c instanceof UikitButton) {
            if (eventId == UikitButton.EVENT_RELEASED) {
                if (((UikitButton) c).getId() == ALERT_DIALOG) {
                } else if (((UikitButton) c).getId() == ALERT_DIALOG_YES) {
                    midlet.notifyDestroyed();
                } else if (((UikitButton) c).getId() == INPUT_DIALOG_OK) {
                    InputDialog d = (InputDialog) c.getContainingPanel().getContainingPanel().getContainingPanel();
                    String input = d.getInput();
                    System.out.println("Input : " + input);
                }
                // dismiss dialog
                view.getLayer(LAYER_DIALOG).removeAllComponents();
                canvas.setTouchEventHandler(currentTouchHandler);
            }
        }
    }

    private void showYesNoDialog(String title, String message) {
        int width = UiKitDisplay.getWidth() * 80 / 100;
        int height = width * 75 / 100;
        int desc_color = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_DESC_TEXT_COLOR));

        AlertDialog dialog = new AlertDialog((UiKitDisplay.getWidth() - width) / 2, (UiKitDisplay.getHeight() - height) / 2, width, height, title);
        dialog.setTitle(title);
        dialog.setAlertText(message);
        Utils.applyTextStyles(dialog, 0, desc_color);
        dialog.setIcon(Resources.getInstance().getThemeImage(GraphicsResources.IMG_ICON_SMALL));
        dialog.setStyle(Utils.getDialogComponentStyle());

        UikitButton btnYes = Utils.getButton(Resources.getInstance().getText(GlobalResources.TXT_COMMON_YES), width * 40 / 100);
        btnYes.setId(ALERT_DIALOG_YES);
        UikitButton btnNo = Utils.getButton(Resources.getInstance().getText(GlobalResources.TXT_COMMON_NO), width * 40 / 100);
        btnNo.setId(ALERT_DIALOG_NO);
        dialog.addButton(btnNo);
        dialog.addButton(btnYes);

        btnYes.setEventListener(this);
        btnNo.setEventListener(this);
        view.getLayer(LAYER_DIALOG).addComponent(dialog);
        currentTouchHandler = canvas.getTouchEventHandler();
        canvas.setTouchEventHandler(new TouchEventHandler(dialog.getContainerPanel()));
    }

    public void showAlertDialog(String title, String message) {
        int width = UiKitDisplay.getWidth() * 80 / 100;
        int height = width * 75 / 100;
        int desc_color = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_DESC_TEXT_COLOR));


        AlertDialog dialog = new AlertDialog((UiKitDisplay.getWidth() - width) / 2, (UiKitDisplay.getHeight() - height) / 2, width, height, title);
        dialog.setTitle(title);
        dialog.setAlertText(message);
        Utils.applyTextStyles(dialog, 0, desc_color);
        dialog.setIcon(Resources.getInstance().getThemeImage(GraphicsResources.IMG_ICON_SMALL));


        dialog.setStyle(Utils.getDialogComponentStyle());
        int gap = 10;
        UikitButton btn = Utils.getButton(Resources.getInstance().getText(GlobalResources.TXT_COMMON_OK), (dialog.getWidth() - (gap * 3)) / 2);
        btn.setId(ALERT_DIALOG);
        dialog.addButton(btn);

        btn.setEventListener(this);
        view.getLayer(LAYER_DIALOG).addComponent(dialog);
        currentTouchHandler = canvas.getTouchEventHandler();
        canvas.setTouchEventHandler(new TouchEventHandler(dialog.getContainerPanel()));
    }

    public void showInputDialog(String title, String message, int entryId) {
        int width = UiKitDisplay.getWidth() * 80 / 100;
        int height = width * 75 / 100;

        Image imgFont = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_MEDIUM);
        BitmapFont f = new BitmapFont(imgFont, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_MEDIUM, 0);


        InputDialog dialog = new InputDialog((UiKitDisplay.getWidth() - width) / 2, (UiKitDisplay.getHeight() - height) / 2, width, height, false, false, message, TextField.ANY);
        dialog.setTitle(title);
        dialog.setStyle(Utils.getDialogComponentStyle());
        dialog.setId(entryId);

        dialog.setIcon(Resources.getInstance().getThemeImage(GraphicsResources.IMG_ICON_SMALL));
        Utils.applyTextFieldStyles(dialog.getTextInput(), f);

        UikitButton btnYes = Utils.getButton(Resources.getInstance().getText(GlobalResources.TXT_COMMON_OK), width * 40 / 100);
        btnYes.setId(INPUT_DIALOG_OK);
        dialog.addButton(btnYes);
        UikitButton btnNo = Utils.getButton(Resources.getInstance().getText(GlobalResources.TXT_COMMON_CANCEL), width * 40 / 100);
        btnNo.setId(INPUT_DIALG_CANCEL);
        dialog.addButton(btnNo);

        btnYes.setEventListener(this);
        btnNo.setEventListener(this);
        view.getLayer(LAYER_DIALOG).addComponent(dialog);

        currentTouchHandler = canvas.getTouchEventHandler();
        canvas.setTouchEventHandler(new TouchEventHandler(dialog.getContainerPanel()));
    }

    private void confirmExit() {
        String title = Resources.getInstance().getText(GlobalResources.TXT_DIALOG_EXIT_TITLE);
        String msg = Resources.getInstance().getText(GlobalResources.TXT_DIALOG_EXIT_DESC);
        showYesNoDialog(title, msg);
    }

    public void showPinCodeDialog() {
        //#if FULL
        showInputDialog("title", Resources.getInstance().getText(GlobalResources.TXT_DIALOG_PIN_MSG), INPUT_DIALOG_OK);
        //#else
//#         showAlertDialog(Resources.getInstance().getText(GlobalResources.TXT_DIALOG_TITLE_UNAUTHORISED), Resources.getInstance().getText(GlobalResources.TXT_DIALOG_DESC_UNAUTHORISED));
        //#endif 
    }
}
