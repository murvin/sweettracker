package com.sweettracker;

import com.sweettracker.model.Date;
import com.sweettracker.model.Settings;
import com.sweettracker.model.User;
import com.sweettracker.ui.AboutScreen;
import com.sweettracker.ui.CalScreen;
import com.sweettracker.ui.ChartScreen;
import com.sweettracker.ui.EntryScreen;
import com.sweettracker.ui.HelpScreen;
import com.sweettracker.ui.HomeScreen;
import com.sweettracker.ui.LocaleScreen;
import com.sweettracker.ui.SettingsScreen;
import com.sweettracker.ui.SplashScreen;
import com.sweettracker.ui.TermsAndConditionsScreen;
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
import com.uikit.styles.TextStyle;
import com.uikit.utils.UikitConstant;
import java.io.IOException;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;

public class SweetTrackerController extends Controller {

    // LAYER CONSTANTS //
    private final int LAYER_SCREEN = 0x011;
    private final int LAYER_DIALOG = 0x021;
    private final int LAYER_NAVIGATION = 0x020;
    // SCREEN CONSTANTS //
    public static final int SCREEN_SPLASH = 0x010;
    public static final int SCREEN_HOME = 0x011;
    public static final int SCREEN_SETTINGS = 0x012;
    public static final int SCREEN_CAL = 0x013;
    public static final int SCREEN_ENTRY = 0x014;
    public static final int SCREEN_ABOUT = 0x015;
    public static final int SCREEN_TERMS = 0x016;
    public static final int SCREEN_LOCALE = 0x017;
    public static final int SCREEN_CHART = 0x018;
    public static final int SCREEN_HELP = 0x019;
    // MENU CONSTANTS 
    public static final int MENU_EXIT = 0x101;
    public static final int MENU_BACK = 0x102;
    public static final int MENU_ABOUT = 0x103;
    public static final int MENU_OK = 0x108;
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
    private final String LOCALE_BUNDLE_NAME = "GlobalResources";
    private final String THEME_BUNDLE_NAME = "GraphicsResources";

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
            Resources.getInstance().initResources(LOCALE_BUNDLE_NAME, Utils.getEntryText(Utils.ENTRY_LOCALES, settings.getCurrentLocale()));
            Resources.getInstance().initTheme(THEME_BUNDLE_NAME, Utils.getEntryText(Utils.ENTRY_THEMES, settings.getCurrentTheme()));
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
            menuBar.removeSoftKey(true);
        } else if (screen instanceof SettingsScreen) {
            topBar.setLabel(Resources.getInstance().getText(GlobalResources.TXT_MENU_SETTINGS));
            menuBar.setRsk(Resources.getInstance().getText(GlobalResources.TXT_COMMON_BACK), MENU_BACK);
            menuBar.setLsk(Resources.getInstance().getText(GlobalResources.TXT_COMMON_SAVE), MENU_SAVE);
        } else if (screen instanceof CalScreen) {
            topBar.setLabel(Resources.getInstance().getText(GlobalResources.TXT_MENU_CAL));
            menuBar.setRsk(Resources.getInstance().getText(GlobalResources.TXT_COMMON_BACK), MENU_BACK);
            menuBar.removeSoftKey(true);
        } else if (screen instanceof EntryScreen) {
            topBar.setLabel(Resources.getInstance().getText(GlobalResources.TXT_MENU_ENTRY));
            menuBar.setRsk(Resources.getInstance().getText(GlobalResources.TXT_COMMON_BACK), MENU_BACK);
            menuBar.setLsk(Resources.getInstance().getText(GlobalResources.TXT_COMMON_SAVE), MENU_SAVE);
        } else if (screen instanceof AboutScreen) {
            topBar.setLabel(Resources.getInstance().getText(GlobalResources.TXT_COMMON_ABOUT));
            menuBar.setRsk(Resources.getInstance().getText(GlobalResources.TXT_COMMON_BACK), MENU_BACK);
            menuBar.removeSoftKey(true);
        } else if (screen instanceof TermsAndConditionsScreen) {
            topBar.setLabel(Resources.getInstance().getText(GlobalResources.TXT_TERMS));
            menuBar.setRsk(Resources.getInstance().getText(GlobalResources.TXT_MENU_ACCEPT), MENU_ACCEPT);
            menuBar.setLsk(Resources.getInstance().getText(GlobalResources.TXT_MENU_DECLINE), MENU_DECLINE);
        } else if (screen instanceof LocaleScreen) {
            topBar.setLabel(Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_LANGUAGE_TITLE));
            menuBar.setRsk(Resources.getInstance().getText(GlobalResources.TXT_COMMON_SAVE), MENU_SAVE);
            menuBar.removeSoftKey(true);
        } else if (screen instanceof ChartScreen) {
            topBar.setLabel(Resources.getInstance().getText(GlobalResources.TXT_MENU_GRAPH));
            menuBar.setRsk(Resources.getInstance().getText(GlobalResources.TXT_COMMON_BACK), MENU_BACK);
            menuBar.removeSoftKey(true);
        } else if (screen instanceof HelpScreen) {
            topBar.setLabel(Resources.getInstance().getText(GlobalResources.TXT_COMMON_HELP));
            menuBar.setRsk(Resources.getInstance().getText(GlobalResources.TXT_COMMON_BACK), MENU_BACK);
            menuBar.removeSoftKey(true);
        }
        screen.enter();
    }

    public void addScreen(Screen screen) {
        if (screen instanceof SplashScreen) {
            screen.x = 0;
            screen.y = 0;
        } else {
            screen.y = 0;
            if (previous_screen_id == SCREEN_SETTINGS
                    || previous_screen_id == SCREEN_CAL
                    || previous_screen_id == SCREEN_ABOUT
                    || previous_screen_id == SCREEN_TERMS
                    || previous_screen_id == SCREEN_LOCALE
                    || previous_screen_id == SCREEN_CHART
                    || previous_screen_id == SCREEN_HELP
                    || previous_screen_id == SCREEN_ENTRY) {
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
                return new HomeScreen();
            }
            case SCREEN_SETTINGS: {
                return new SettingsScreen();
            }
            case SCREEN_CAL: {
                return new CalScreen();
            }
            case SCREEN_ENTRY: {
                return new EntryScreen((Date) param);
            }
            case SCREEN_ABOUT: {
                return new AboutScreen();
            }
            case SCREEN_TERMS: {
                return new TermsAndConditionsScreen();
            }
            case SCREEN_LOCALE: {
                return new LocaleScreen();
            }
            case SCREEN_CHART: {
                return new ChartScreen();
            }
            case SCREEN_HELP: {
                return new HelpScreen();
            }
            default:
                throw new IllegalStateException();
        }
    }

    public void onSplashFinish() {
        Settings settings = null;
        try {
            settings = (Settings) Database.getInstance().retrieveISerializable(Database.SETTINGS);
            if (settings.hasAcceptedTerms()) {
                //#if FULL_VERSION
                if (settings.hasDefaultCode()) {
                    navigateScreen(SweetTrackerController.SCREEN_HOME, true, null);
                } else {
                    showConfirmPinCodeDialog();
                }
                //#else
//#                     navigateScreen(SweetTrackerController.SCREEN_HOME, true, null);
                //#endif
            } else {
                navigateScreen(SweetTrackerController.SCREEN_LOCALE, false, null);
            }
            topBar.enter();
            menuBar.enter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onComponentEvent(Component c, int eventId, Object o, int param) {
        super.onComponentEvent(c, eventId, o, param);
        if (c instanceof HomeScreen) {
            if (eventId == HomeScreen.MENU_CLICK) {
                if (param == 3) {
                    navigateScreen(SCREEN_SETTINGS, false, null);
                } else if (param == 1) {
                    navigateScreen(SCREEN_CAL, false, null);
                } else if (param == 0) {
                    navigateScreen(SCREEN_ENTRY, false, Utils.getCurrentDate());
                } else if (param == 4) {
                    navigateScreen(SCREEN_ABOUT, false, null);
                } else if (param == 2) {
                    navigateScreen(SCREEN_CHART, false, null);
                } else if (param == 5) {
                    navigateScreen(SCREEN_HELP, false, null);
                }
            }
        } else if (c instanceof MenuBar) {
            if (eventId == MENU_BACK) {
                if (previous_screen_id == SCREEN_CAL) {
                    navigateScreen(SCREEN_CAL, false, null);
                } else {
                    navigateScreen(SCREEN_HOME, false, null);
                }
            } else if (eventId == MENU_EXIT) {
                confirmExit();
            } else if (eventId == MENU_SAVE) {
                if (current_screen instanceof SettingsScreen) {
                    boolean hasLocaleChanged = ((SettingsScreen) current_screen).hasLocaleChanged();
                    boolean hasThemeChanged = ((SettingsScreen) current_screen).hasThemeChanged();
                    ((SettingsScreen) current_screen).saveSettings();
                    Settings settings = ((SettingsScreen) current_screen).getCurrentSettings();
                    if (hasLocaleChanged) {
                        try {
                            Resources.getInstance().initResources(LOCALE_BUNDLE_NAME, Utils.getEntryText(Utils.ENTRY_LOCALES, settings.getCurrentLocale()));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (hasThemeChanged) {
                        try {
                            Resources.getInstance().initTheme(THEME_BUNDLE_NAME, Utils.getEntryText(Utils.ENTRY_THEMES, settings.getCurrentTheme()));
                            topBar.updateTheme();
                            menuBar.updateTheme();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    navigateScreen(SweetTrackerController.SCREEN_HOME, false, null);
                    showAlertDialog(Resources.getInstance().getText(GlobalResources.TXT_DIALOG_TITLE_SUCCESSFUL), Resources.getInstance().getText(GlobalResources.TXT_SETTINGS_SAVED));
                } else if (current_screen instanceof EntryScreen) {
                    ((EntryScreen) current_screen).saveEntry();
                    navigateScreen(previous_screen_id, false, null);
                    showAlertDialog(Resources.getInstance().getText(GlobalResources.TXT_DIALOG_ENTRY_SAVED_TITLE), Resources.getInstance().getText(GlobalResources.TXT_DIALOG_ENTRY_SAVED_MSG));
                } else if (current_screen instanceof LocaleScreen) {
                    ((LocaleScreen) current_screen).saveLocale();
                    try {
                        Resources.getInstance().initResources(LOCALE_BUNDLE_NAME, Utils.getEntryText(Utils.ENTRY_LOCALES, ((LocaleScreen) current_screen).getSelectedIndex()));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    navigateScreen(SCREEN_TERMS, false, null);
                }
            } else if (eventId == MENU_DECLINE) {
                midlet.notifyDestroyed();
            } else if (eventId == MENU_ACCEPT) {
                navigateScreen(SCREEN_HOME, true, null);
                saveTermsAccepted();
            }
        } else if (c instanceof UikitButton) {
            if (eventId == UikitButton.EVENT_RELEASED) {
                if (((UikitButton) c).getId() == ALERT_DIALOG) {
                } else if (((UikitButton) c).getId() == ALERT_DIALOG_YES) {
                    midlet.notifyDestroyed();
                } else if (((UikitButton) c).getId() == INPUT_DIALOG_OK) {
                    InputDialog d = (InputDialog) c.getContainingPanel().getContainingPanel().getContainingPanel();
                    String input = d.getInput();
                    if (input == null || input.trim().equals("")) {
                        if (d.getId() == SettingsScreen.INPUT_CONFIRM_PIN) {
                            midlet.notifyDestroyed();
                        } else {
                            // dismiss dialog
                            view.getLayer(LAYER_DIALOG).removeAllComponents();
                            canvas.setTouchEventHandler(currentTouchHandler);
                            showAlertDialog(Resources.getInstance().getText(GlobalResources.TXT_DIALOG_TITLE_UNSUCCESSFUL),
                                    Resources.getInstance().getText(GlobalResources.TXT_DIALOG_TEXT_INVALID_INPUT));
                        }
                        return;
                    } else {
                        if (current_screen instanceof SettingsScreen) {
                            if (d.getId() == SettingsScreen.INPUT_PIN) {
                                ((SettingsScreen) current_screen).setSettingsPinCode(input);
                            } else if (d.getId() == SettingsScreen.INPUT_TARGET) {
                                ((SettingsScreen) current_screen).setTargetLevel(Float.parseFloat(input));
                            }
                        } else if (current_screen instanceof EntryScreen) {
                            if (d.getId() == EntryScreen.INPUT_GLUCOSE_LEVEL) {
                                ((EntryScreen) current_screen).setGlucoseLevel(Float.parseFloat(input));
                            } else if (d.getId() == EntryScreen.INPUT_GLUCOSE_NOTE) {
                                ((EntryScreen) current_screen).setNote(input);
                            }
                        } else {
                            if (d.getId() == SettingsScreen.INPUT_CONFIRM_PIN) {
                                confirmPIN(input);
                            }
                        }
                    }
                } else if (((UikitButton) c).getId() == INPUT_DIALG_CANCEL) {
                    InputDialog d = (InputDialog) c.getContainingPanel().getContainingPanel().getContainingPanel();
                    if (d.getId() == SettingsScreen.INPUT_CONFIRM_PIN) {
                        midlet.notifyDestroyed();
                    }
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

    public void showInputDialog(String title, String message, int entryId, int fieldType, String content) {
        int width = UiKitDisplay.getWidth() * 80 / 100;
        int height = width * 75 / 100;

        Image imgFont = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_MEDIUM);
        BitmapFont f = new BitmapFont(imgFont, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_MEDIUM, 0);

        int desc_color = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_DESC_TEXT_COLOR));
        Image imgFontDesc = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_SMALL);
        BitmapFont descFont = new BitmapFont(imgFontDesc, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_SMALL, 0);

        TextStyle txtStyleTitle = new TextStyle(f);
        txtStyleTitle.setFontColour(0);
        txtStyleTitle.setAlign(UikitConstant.HCENTER);

        TextStyle txtStyleDesc = new TextStyle(descFont);
        txtStyleDesc.setFontColour(desc_color);
        txtStyleDesc.setAlign(UikitConstant.LEFT);

        InputDialog dialog = new InputDialog((UiKitDisplay.getWidth() - width) / 2, (UiKitDisplay.getHeight() - height) / 2, width, height, false, false, message, fieldType);
        dialog.setTitle(title);
        dialog.setStyle(Utils.getDialogComponentStyle());
        dialog.setId(entryId);
        if (content != null) {
            dialog.setContentText(content);
        }

        dialog.setIcon(Resources.getInstance().getThemeImage(GraphicsResources.IMG_ICON_SMALL));
        Utils.applyTextFieldStyles(dialog.getTextInput(), f);

        dialog.setTitleTextStyle(txtStyleTitle);
        dialog.setStyle(InputDialog.COMP_TEXT_LABEL, txtStyleDesc);

        UikitButton btnYes = Utils.getButton(Resources.getInstance().getText(GlobalResources.TXT_COMMON_OK), width * 43 / 100);
        btnYes.setId(INPUT_DIALOG_OK);
        dialog.addButton(btnYes);
        UikitButton btnNo = Utils.getButton(Resources.getInstance().getText(GlobalResources.TXT_COMMON_CANCEL), width * 43 / 100);
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

    private void saveTermsAccepted() {
        Settings settings = null;
        try {
            settings = (Settings) Database.getInstance().retrieveISerializable(Database.SETTINGS);
            settings.setHasAcceptedTerms(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Database.getInstance().saveISerializable(settings, Database.SETTINGS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showConfirmPinCodeDialog() {
        showInputDialog(Resources.getInstance().getText(GlobalResources.TXT_DIALOG_PIN_TITLE), Resources.getInstance().getText(GlobalResources.TXT_DIALOG_PIN_MSG_CONFIRM), SettingsScreen.INPUT_CONFIRM_PIN, TextField.DECIMAL, null);
    }

    private void confirmPIN(String code) {
        Settings settings = null;
        try {
            settings = (Settings) Database.getInstance().retrieveISerializable(Database.SETTINGS);

            if (settings.getCode().equals(code)) {
                navigateScreen(SCREEN_HOME, true, null);
            } else {
                midlet.notifyDestroyed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showPinCodeDialog() {
        //#if FULL_VERSION
        showInputDialog(Resources.getInstance().getText(GlobalResources.TXT_DIALOG_PIN_TITLE), Resources.getInstance().getText(GlobalResources.TXT_DIALOG_PIN_MSG), SettingsScreen.INPUT_PIN, TextField.DECIMAL, null);
        //#else
//#         showAlertDialog(Resources.getInstance().getText(GlobalResources.TXT_DIALOG_TITLE_UNAUTHORISED), Resources.getInstance().getText(GlobalResources.TXT_DIALOG_DESC_UNAUTHORISED));
        //#endif 
    }

    public void showTargetLevelDialog() {
        //#if FULL_VERSION
        showInputDialog(Resources.getInstance().getText(GlobalResources.TXT_DIALOG_TARGET_LEVEL_TITLE), Resources.getInstance().getText(GlobalResources.TXT_DIALOG_TARGET_LEVEL_MSG), SettingsScreen.INPUT_TARGET, TextField.DECIMAL, null);
        //#else
//#         showAlertDialog(Resources.getInstance().getText(GlobalResources.TXT_DIALOG_TITLE_UNAUTHORISED), Resources.getInstance().getText(GlobalResources.TXT_DIALOG_DESC_UNAUTHORISED));
        //#endif 
    }

    public void showGlucoseLevelDialog() {
        showInputDialog(Resources.getInstance().getText(GlobalResources.TXT_DIALOG_GLUCOSE_LEVEL_TITLE), Resources.getInstance().getText(GlobalResources.TXT_DIALOG_GLUCOSE_LEVEL_MSG), EntryScreen.INPUT_GLUCOSE_LEVEL, TextField.DECIMAL, null);
    }

    public void showGlucoseLevelNoteDialog(String note) {
        showInputDialog(Resources.getInstance().getText(GlobalResources.TXT_MENU_ENTRY), Resources.getInstance().getText(GlobalResources.TXT_TAP_TO_ADD_NOTE), EntryScreen.INPUT_GLUCOSE_NOTE, TextField.ANY, note);
    }
}
