package com.sweettracker.ui;

import com.sweettracker.SweetTrackerController;
import com.sweettracker.ui.components.ClippedImage;
import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.uikit.animations.UikitImageBox;
import com.uikit.coreElements.Component;
import com.uikit.painters.BgImagePainter;
import com.uikit.utils.UikitConstant;
import javax.microedition.lcdui.Image;

public class SplashScreen extends SweetTrackerScreen {

    private Image imgBg, imgGraph, imgGrid, imgAppName, imgDevName;
    private ClippedImage clippedImage;
    private int vgap;

    public SplashScreen() {
        super();
        initResources();
        initComponents();

        clippedImage.reveal();
    }

    private void initResources() {
        imgBg = Resources.getInstance().getThemeImage(GraphicsResources.IMG_SPLASH_BG);
        imgGraph = Resources.getInstance().getThemeImage(GraphicsResources.IMG_SPLASH_GRAPH);
        imgGrid = Resources.getInstance().getThemeImage(GraphicsResources.IMG_SPLASH_GRID);
        imgAppName = Resources.getInstance().getThemeImage(GraphicsResources.IMG_SPLASH_APPNAME);
        imgDevName = Resources.getInstance().getThemeImage(GraphicsResources.IMG_SPLASH_DEVNAME);
        vgap = 5;
    }

    private void initComponents() {
        getStyle(true).addRenderer(new BgImagePainter(imgBg, UikitConstant.NOREPEAT));
        setLayout(null);

        UikitImageBox imageGrid = new UikitImageBox(imgGrid);
        imageGrid.x = (iWidth - imageGrid.getWidth()) / 2;
        imageGrid.y = (iHeight - imageGrid.getHeight()) / 2;
        addComponent(imageGrid);


        UikitImageBox imageAppName = new UikitImageBox(imgAppName);
        imageAppName.x = (iWidth - imageAppName.getWidth()) / 2;
        imageAppName.y = (imageGrid.y / 2) - (imageAppName.getHeight() / 2);
        addComponent(imageAppName);

        clippedImage = new ClippedImage(imgGraph);
        clippedImage.setEventListener(this);
        clippedImage.x = (iWidth - clippedImage.getWidth()) / 2;
        clippedImage.y = (iHeight - clippedImage.getHeight()) / 2;
        addComponent(clippedImage);

        UikitImageBox imageDevName = new UikitImageBox(imgDevName);
        imageDevName.x = (iWidth - imageDevName.getWidth()) / 2;
        imageDevName.y = iHeight - imageDevName.getHeight() - this.vgap;
        addComponent(imageDevName);
    }

    public void enter() {
        super.notifyEnterFinished();
    }

    public void onComponentEvent(Component c, int e, Object o, int p) {
        super.onComponentEvent(c, e, o, p);
        if (c == clippedImage && e == ClippedImage.EVENT_ANIMATION_FINISH) {
            ((SweetTrackerController) controller).onSplashFinish();
        }
    }
}
