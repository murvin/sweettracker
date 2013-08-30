package com.sweettracker.ui.components;

import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
//#if !ASHA_501
//# import com.sweettracker.utils.Utils;
//# import com.uikit.coreElements.IUikitInputHandler;
//#endif 
import com.uikit.motion.MotionLinear;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.coreElements.Panel;

import com.uikit.painters.PatchPainter;
import javax.microedition.lcdui.Image;

public class MenuBar extends Panel  
         //#if !ASHA_501
//#        implements IUikitInputHandler {
        //#else
        {
 //#endif 

    private Image bg;
    private MenuBarItem lsk, rsk;
    private int patch;
    private int softKeyPadding;
    //#if !ASHA_501
//#     private int offset;
    //#endif

    public MenuBar() {
        super(UiKitDisplay.getWidth(), 0);
        initResources();
        initComponents();

        setSize(iWidth, bg.getHeight());
    }

    public void updateTheme() {
        initResources();
        getStyle(true).clearAllRenderers();
        initComponents();
    }

    public void removeSoftKey(boolean isLSK) {
        if (isLSK) {
            if (this.lsk != null && this.lsk.getContainingPanel() == this) {
                removeComponent(lsk);
            }
        } else {
            if (this.rsk != null && this.rsk.getContainingPanel() == this) {
                removeComponent(rsk);
            }
        }
    }

    private void initResources() {
        patch = 5;
        this.bg = Resources.getInstance().getThemeImage(GraphicsResources.IMG_MENUBAR_TILE);
        this.softKeyPadding = 3 * UiKitDisplay.getWidth() / 100;
        //#if !ASHA_501
//#         this.offset = 1 * UiKitDisplay.getWidth() / 100;
        //#endif
    }

    private void initComponents() {
        getStyle(true).addRenderer(new PatchPainter(bg, patch, patch, patch, patch));
    }

    public void setLsk(String text, int id) {
        if (this.lsk == null) {
            lsk = new MenuBarItem(bg.getHeight(), true);
            lsk.setEventListener(cel);
        }

        if (lsk.getContainingPanel() == null) {
            addComponent(lsk);
        }

        lsk.setText(text);
        lsk.y = (iHeight - lsk.getHeight()) / 2;
        lsk.x = softKeyPadding;
        lsk.setId(id);
    }

    public void setRsk(String text, int id) {
        if (this.rsk == null) {
            rsk = new MenuBarItem(bg.getHeight(), false);
            rsk.setEventListener(cel);
        }

        if (rsk.getContainingPanel() == null) {
            addComponent(rsk);
        }

        rsk.setText(text);
        rsk.y = (iHeight - rsk.getHeight()) / 2;
        rsk.x = iWidth - rsk.getWidth() - softKeyPadding;
        rsk.setId(id);
    }

    public void enter() {
        MotionLinear mfx_slide = new MotionLinear(1, MotionLinear.MOFX_LINEAR_PULLBACK);
        ((MotionLinear) mfx_slide).init(x, y, 0, y, 10, 0.0f, 0.0f);
        this.motionFX = mfx_slide;
    }
 //#if !ASHA_501
//#     public boolean onKeyPressed(int iKeyCode) {
//#         if (iKeyCode == Utils.LSK) {
//#             if (this.lsk != null) {
//#                 this.lsk.x += offset;
//#                 this.lsk.y += offset;
//#             }
//#         } else if (iKeyCode == Utils.RSK) {
//#             if (this.rsk != null) {
//#                 this.rsk.x += offset;
//#                 this.rsk.y += offset;
//#             }
//#         }
//#         return false;
//#     }
//# 
//#     public boolean onKeyReleased(int iKeyCode) {
//#         if (iKeyCode == Utils.LSK) {
//#             if (this.lsk != null) {
//#                 this.lsk.x -= offset;
//#                 this.lsk.y -= offset;
//# 
//#                 if (cel != null) {
//#                     cel.onComponentEvent(this, this.lsk.getId(), null, -1);
//#                 }
//#             }
//#         } else if (iKeyCode == Utils.RSK) {
//#             if (this.rsk != null) {
//#                 this.rsk.x -= offset;
//#                 this.rsk.y -= offset;
//# 
//#                 if (cel != null) {
//#                     cel.onComponentEvent(this, this.rsk.getId(), null, -1);
//#                 }
//#             }
//#         }
//#         return false;
//#     }
//# 
//#     public boolean onKeyRepeated(int iKeyCode) {
//#         return false;
//#     }
     //#endif
}
