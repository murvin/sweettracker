package com.sweettracker.ui.components.settings;

import com.sweettracker.utils.Utils;
import com.uikit.animations.Line;
import com.uikit.animations.UikitImageBox;
import com.uikit.animations.UikitTextBox;
import com.uikit.coreElements.BitmapFont;
import com.uikit.coreElements.IComponentEventListener;
import com.uikit.coreElements.Panel;
import com.uikit.layout.BoxLayout;
import com.uikit.styles.TextStyle;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Image;

public abstract class SettingsItem extends Panel {

    protected String title;
    protected String desc;
    protected BitmapFont title_font, desc_font;
    protected int title_color, desc_color;
    private int vgap;
    protected Object param;
    private TextStyle txtDescStyle;

    public SettingsItem(int w, String title, String desc, BitmapFont title_font, BitmapFont desc_font, int title_color, int desc_color, Object param, IComponentEventListener _cel) {
        super(w, 0);
        this.cel = _cel;
        this.title = title;
        this.desc = desc;
        this.title_font = title_font;
        this.desc_font = desc_font;
        this.title_color = title_color;
        this.desc_color = desc_color;
        vgap = 3;
        this.param = param;

        initComponents();
        addLine();

        BoxLayout l = new BoxLayout(UikitConstant.VERTICAL, vgap);
        l.setAlign(UikitConstant.LEFT);
        setLayout(l);
    }

    protected void initComponents() {
        addTitle();
        addDesc();
        addContent();
    }

    public abstract void addContent();

    private void addTitle() {
        Image imgText = title_font.drawStringToImage(title);
        imgText = Utils.replaceColor(imgText, title_color);
        addComponent(new UikitImageBox(imgText));
    }

    private void addDesc() {
        txtDescStyle = new TextStyle(desc_font);
        txtDescStyle.setFontColour(desc_color);
        addComponent(new UikitTextBox(iWidth, desc, txtDescStyle));
    }

    private void addLine() {
        Line line = new Line(iWidth, title_color);
        addComponent(line);
    }
}
