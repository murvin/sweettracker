package com.sweettracker.ui;

import com.sweettracker.ui.components.MenuItem;
import com.sweettracker.utils.GlobalResources;
import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.uikit.coreElements.Component;
import com.uikit.layout.GridLayout;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Image;

public class HomeScreen extends SweetTrackerScreen {

    private Image[] menu_icons;
    private String[] menu_labels;
    private int layout_vgap, layout_hgap;
    public static final int MENU_CLICK = 0xff01;

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
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_MENU_ADD),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_MENU_CAL),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_MENU_GRAPH),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_MENU_SETTINGS),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_MENU_INFO),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_MENU_HELP)
        };

        menu_labels = new String[]{
            Resources.getInstance().getText(GlobalResources.TXT_MENU_ADD),
            Resources.getInstance().getText(GlobalResources.TXT_MENU_CAL),
            Resources.getInstance().getText(GlobalResources.TXT_MENU_GRAPH),
            Resources.getInstance().getText(GlobalResources.TXT_MENU_SETTINGS),
            Resources.getInstance().getText(GlobalResources.TXT_MENU_INFO),
            Resources.getInstance().getText(GlobalResources.TXT_MENU_HELP)
        };

        layout_hgap = 0;
        layout_vgap = 0;
    }

    private void initComponents() {
        GridLayout l = new GridLayout(3, 2, iWidth / 2, iHeight / 3, layout_hgap, layout_vgap, UikitConstant.HCENTER | UikitConstant.VCENTER);
        setLayout(l);

        for (int i = 0; i < menu_icons.length; i++) {
            MenuItem item = new MenuItem(menu_icons[i], menu_labels[i], i);
            item.setEventListener(this);
            addComponent(item);
        }
    }

    public void onComponentEvent(Component c, int e, Object o, int p) {
        super.onComponentEvent(c, e, o, p);
        if (c instanceof MenuItem) {
            controller.onComponentEvent(this, MENU_CLICK, menu_labels[p], p);
        }
    }
}
