package com.sweettracker.ui;

import com.sweettracker.model.Settings;
import com.sweettracker.model.User;
import com.sweettracker.model.Date;
import com.sweettracker.model.Entries;
import com.sweettracker.model.visitors.DateVisitor;
import com.sweettracker.ui.components.EntryItem;
import com.sweettracker.utils.Database;
import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.Panel;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.layout.BoxLayout;
import com.uikit.painters.PatchPainter;
import com.uikit.utils.UikitConstant;
import java.io.IOException;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class EntryScreen extends SweetTrackerScreen {

    private int vgap;
    private Date date;
    private Image[] icons;
    private Image imgNewEntryBg;
    private BitmapFont font_title, font_desc;
    private int font_title_color, font_desc_color, line_seperator_color;
    private Settings settings;
    private User user;
    private Panel container;
    private EntryItem entryCal, entryTime, entryNote;

    public EntryScreen(Date date) {
        this.date = date;
        init();
    }

    private void init() {
        initResources();
        initComponents();
    }

    private void initResources() {
        icons = new Image[]{
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_CAL_ICON),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_TIME_ICON),
            Resources.getInstance().getThemeImage(GraphicsResources.IMG_PEN_ICON)
        };

        imgNewEntryBg = Resources.getInstance().getThemeImage(GraphicsResources.IMG_HIGH_BG);

        line_seperator_color = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_LINE_SEPERATOR_COLOR));

        font_title_color = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_TITLE_TEXT_COLOR));
        Image imgFontTitle = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_MEDIUM);
        font_title = new BitmapFont(imgFontTitle, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_MEDIUM, 0);

        font_desc_color = Integer.parseInt(Resources.getInstance().getThemeStr(GraphicsResources.TXT_DESC_TEXT_COLOR));
        Image imgFontDesc = Resources.getInstance().getThemeImage(GraphicsResources.FONT_THEME_SMALL);
        font_desc = new BitmapFont(imgFontDesc, Utils.FONT_CHARS, Font.STYLE_PLAIN, Font.SIZE_SMALL, 0);
        padding = 4 * UiKitDisplay.getWidth() / 100;

        vgap = 2 * UiKitDisplay.getHeight() / 100;

        try {
            settings = (Settings) Database.getInstance().retrieveISerializable(Database.SETTINGS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            user = (User) Database.getInstance().retrieveISerializable(Database.USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setContainerBg(Entries entries) {
        if (entries == null) {
            container.getStyle(true).addRenderer(new PatchPainter(imgNewEntryBg, 2, 2, 2, 2));
        } else {
            DateVisitor d = new DateVisitor(this.date);
            try {
                entries.accept(d);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void initComponents() {
        container = new Panel(iWidth - (2 * vgap), iHeight - bottomPadding - topPadding - (vgap * 2));
        
        setContainerBg(user.getEntries());
        addComponent(container);
        container.setLayout(new BoxLayout(UikitConstant.VERTICAL, vgap));
        
        entryCal = new EntryItem(container.getWidth(), icons[0], "Cal", "Swipe item to edit", font_title, font_desc, line_seperator_color, this);
        container.addComponent(entryCal);
        
        entryTime = new EntryItem(container.getWidth(), icons[1], "time", "Swipe item to edit", font_title, font_desc, line_seperator_color, this);
        container.addComponent(entryTime);
        
        updateOffsets();
        getStyle(true).setPadding(topPadding + vgap, 0, bottomPadding + vgap, 0);
    }
}
