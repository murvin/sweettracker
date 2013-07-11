package com.sweettracker.ui;

import com.sweettracker.model.Settings;
import com.sweettracker.ui.components.MenuItem;
import com.sweettracker.utils.Database;
import com.sweettracker.utils.GlobalResources;
import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;
import com.uikit.animations.UikitImageBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.Panel;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.layout.GridLayout;
import com.uikit.utils.ImageUtil;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class HomeScreen extends SweetTrackerScreen {

    private Image[] menu_icons;
    private String[] menu_labels;
    private int layout_vgap, layout_hgap;
    public static final int MENU_CLICK = 0xff01;
    private int vgap;
    private Image lock_on, lock_off;
    private String str_lock_on, str_lock_off;
    private Settings settings;
    private int label_colour;
    private BitmapFont label_font;

    public HomeScreen(int w, int h) {
        super(w, h);
        init();
    }

    public HomeScreen() {
        init();
    }

    private void init() {
        initResources();
        initComponents();
    }

    private void initResources() {
        menu_icons = new Image[]{
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_MENU_CAL),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_MENU_GRAPH),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_MENU_SETTINGS),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_MENU_INFO),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_MENU_HELP)
        };

        menu_labels = new String[]{
            Resources.getInstance().getText(GlobalResources.TXT_MENU_CAL),
            Resources.getInstance().getText(GlobalResources.TXT_MENU_GRAPH),
            Resources.getInstance().getText(GlobalResources.TXT_MENU_SETTINGS),
            Resources.getInstance().getText(GlobalResources.TXT_MENU_INFO),
            Resources.getInstance().getText(GlobalResources.TXT_MENU_HELP)
        };

        lock_off = Resources.getInstance().getThemeImage(GraphicsResources.IMG_LOCK_OFF);
        lock_on = Resources.getInstance().getThemeImage(GraphicsResources.IMG_LOCK_ON);

        str_lock_off = Resources.getInstance().getText(GlobalResources.TXT_LOCK_OFF);
        str_lock_on = Resources.getInstance().getText(GlobalResources.TXT_LOCK_ON);

        label_colour = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_TITLE_TEXT_COLOR));

        Image imgFont = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_SMALL);
        label_font = new BitmapFont(imgFont, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_SMALL, 0);

        layout_hgap = 0;
        layout_vgap = 0;
        vgap = 2 * UiKitDisplay.getHeight() / 100;

        try {
            settings = (Settings) Database.getInstance().retrieveISerializable(Database.SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        setLayout(null);
        int viewPort = iHeight - (Resources.getInstance().getThemeImage(GraphicsResources.IMG_BAR_BG).getHeight() * 2);
        Panel gridContainer = new Panel(iWidth, (viewPort / 3) - 10);
        GridLayout gl = new GridLayout(3, 2, iWidth / 2, (viewPort / 3) - 10, layout_hgap, layout_vgap, UikitConstant.HCENTER | UikitConstant.VCENTER);
        gridContainer.setLayout(gl);

        for (int i = 0; i < menu_icons.length; i++) {
            MenuItem item = new MenuItem(menu_icons[i], menu_labels[i], i);
            item.setEventListener(this);
            gridContainer.addComponent(item);
        }

        gridContainer.y = Resources.getInstance().getThemeImage(GraphicsResources.IMG_BAR_BG).getHeight() + vgap;
        addComponent(gridContainer);

        UikitImageBox imgBoxIcon, imgBoxLabel;
        Image image;
        if (settings.hasDefaultCode()) {
            imgBoxIcon = new UikitImageBox(lock_off);
            image = label_font.drawStringToImage(str_lock_off);
        } else {
            imgBoxIcon = new UikitImageBox(lock_on);
            image = label_font.drawStringToImage(str_lock_on);
        }
        imgBoxIcon.x = vgap / 2;
        imgBoxIcon.y = gridContainer.y + gridContainer.getHeight();
        addComponent(imgBoxIcon);

        image = ImageUtil.replaceColor(image, label_colour);
        imgBoxLabel = new UikitImageBox(image);
        imgBoxLabel.x = imgBoxIcon.x + imgBoxIcon.getWidth() + vgap / 2;
        imgBoxLabel.y = imgBoxIcon.y + ((imgBoxIcon.getHeight() - imgBoxLabel.getHeight()) / 2);
        addComponent(imgBoxLabel);
        
        updateOffsets();
        getStyle(true).setPadding(topPadding, 0, bottomPadding, 0);
        setIsScrollable(false);
    }

    public void onComponentEvent(Component c, int e, Object o, int p) {
        super.onComponentEvent(c, e, o, p);
        if (c instanceof MenuItem) {
            controller.onComponentEvent(this, MENU_CLICK, menu_labels[p], p);
        }
    }
}
