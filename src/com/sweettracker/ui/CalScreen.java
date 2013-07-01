package com.sweettracker.ui;

//#if ADS
//# import com.inbloom.ui.components.AdvertComponent;
//#endif

import com.sweettracker.model.Settings;
import com.sweettracker.ui.components.CalDay;
import com.sweettracker.ui.components.CalMonth;
import com.sweettracker.ui.components.MapKeys;
import com.sweettracker.ui.components.MonthSelector;
import com.sweettracker.utils.Database;
import com.sweettracker.utils.GraphicsResources;
import com.sweettracker.utils.Resources;
import com.sweettracker.utils.Utils;
import com.uikit.coreElements.UiKitDisplay;
import com.uikit.coreElements.Component;
import com.uikit.coreElements.ITouchEventListener;
import com.uikit.coreElements.Panel;

import java.util.Calendar;
import java.util.Date;
import javax.microedition.lcdui.Image;

public class CalScreen extends SweetTrackerScreen {

    private MonthSelector monthSelector;
    private CalMonth month;
    private MapKeys mapKeys;
    /** Actual month and current Month */
    private int mnth, currentMonth;
    /** Actual year and current year*/
    private int year, currentYear;
    /** Current day */
    private int day;
    private Image imgHighLight;
    
     //#if ADS
//#     private final int ADVERT_H = 40;
//#     private AdvertComponent ad;
    //#endif 

    public CalScreen() {
        initResources();
        initComponents();
    }

    private void initResources() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        mnth = c.get(Calendar.MONTH);
        currentMonth = mnth;
        year = c.get(Calendar.YEAR);
        currentYear = year;
        day = c.get(Calendar.DAY_OF_MONTH);
        padding = 4 * UiKitDisplay.getWidth() / 100;

        this.imgHighLight = Resources.getInstance().getThemeImage(GraphicsResources.IMG_HIGHLIGHT_PATCH);
    }

    private void initComponents() {
        //#if ADS
//#         ad = new AdvertComponent(iWidth - (padding * 2), ADVERT_H, InBloomController.myMidlet, null);
//#         ad.downloadAd();
//#         addComponent(ad);
        //#endif
        
        int monthSelectorH = 9 * UiKitDisplay.getHeight() / 100;
        
        this.monthSelector = new MonthSelector(iWidth, monthSelectorH, true);
        this.monthSelector.setCurrent(mnth, year);
        this.monthSelector.setEventListener(this);
        addComponent(monthSelector);

        initCalMonth();
        this.month.setEventListener(this);
        addComponent(this.month);
        this.month.x = UiKitDisplay.getWidth();
        int topOffset = Resources.getInstance().getThemeImage(GraphicsResources.IMG_BAR_BG).getHeight();
        this.month.y += topOffset ;
        this.month.enter();

        this.mapKeys = new MapKeys(iWidth, 40); 

        addComponent(this.mapKeys);
        updateOffsets();
        getStyle(true).setPadding(topPadding, 0, bottomPadding, 0);
    }

    /**
     * Gets the Zeller calculated day of the week for the
     * corresponding year, month and day
     * 
     * @return          A calibrated offset to match calendar's starting 
     *                  day being Monday instead of Saturday (Zeller value = 0)
     */
    private int getCalibratedStartOffset() {
        int startOffset = Utils.getZellerDay(year, mnth, 1);
        if (startOffset == 0) {
            startOffset = 5;
        } else if (startOffset == 1) {
            startOffset = 6;
        } else {
            startOffset -= 2;
        }
        return startOffset;
    }

    private void initCalMonth() {
        Settings settings = null;
        try {
            settings = (Settings) Database.getInstance().retrieveISerializable(Database.SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int startOffset = getCalibratedStartOffset();
        month = new CalMonth(
                iWidth, 10,
                year == currentYear ? (mnth == currentMonth ? day : -1) : -1,
                Utils.getMonthLength(year, mnth),
                new int[]{2,3,4}, // Normal
                new int[]{8,15,28}, // Critical
                new int[]{10, 11, 12} // High
                , this, this.imgHighLight, startOffset);

    }

    private void enterMonth(boolean isNext) {
        CalMonth temp = month;
        initCalMonth();
        month.x = isNext ? UiKitDisplay.getWidth() : -UiKitDisplay.getWidth();
        month.y = temp.y;
        month.isLayoutable = false;
        month.setEventListener(this);
        insertComponentAt(month, 1, false);

        if (isNext) {
            temp.exit();
        } else {
            temp.exitToRight();
        }

        month.enter();

    }

    public void onComponentEvent(Component c, int e, Object o, int p) {
        super.onComponentEvent(c, e, o, p);
        if (c == monthSelector) {
            mnth = e;
            year = p;
            enterMonth(((Boolean) o).booleanValue());
        } else if (c instanceof CalMonth) {
            if (e == CalMonth.EXIT_FINISHED) {
                ((Panel) c).removeAllComponents();
                c.isLayoutable = false;
                removeComponent(c, false);
            } else if (e == CalMonth.ENTER_FINISHED) {
                month.isLayoutable = true;
                month.setEventListener(this);
            }
        } else if (c instanceof CalDay) {
            int d = Integer.parseInt((String) o);
            if (d != -1) {
//                Date date = new Date(d, mnth, year);
//                controller.navigateScreen(InBloomController.SCREEN_ENTRY, true,
//                        new Object[]{Utils.getEntriesForDate(date), date, new Boolean(isPeriodToday)});
            }
        }
    }

    public boolean onDrag(int type, int iStartX, int iStartY, int iDeltaX, int iDeltaY) {
        boolean isHandled = super.onDrag(type, iStartX, iStartY, iDeltaX, iDeltaY);
        if (type == ITouchEventListener.DRAG_RELEASE) {
            if (Math.abs(iDeltaX) > 20) {
                if (iDeltaX < 0) {
                    monthSelector.next();
                    isHandled = true;
                } else if (iDeltaX > 0) {
                    monthSelector.previous();
                    isHandled = true;
                }
            }
        }

        return isHandled;
    }
}
