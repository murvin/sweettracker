
package com.sweettracker.ui;

import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.uikit.painters.BgColorPainter;
import javax.microedition.lcdui.Image;

public class SplashScreen extends SweetTrackerScreen{
    
    private Image imgBg;
    private Image imgGraph;

    public SplashScreen() {
        super();
        initResources();
        initComponents();
        getStyle(true).addRenderer(new BgColorPainter(0x0000bb));
    }
    
    private void initResources(){
        imgBg = Resources.getInstance().getThemeImage(GraphicsResources.IMG_BG_PANEL);
    }
    
    private void initComponents(){
        
    }

    public void enter() {
        super.notifyEnterFinished();
    }
}
