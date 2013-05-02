
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import InneractiveSDK.*;


public class InneractiveSampleMidlet extends MIDlet implements InneractiveAdEventsListener{

	static InneractiveSampleMidlet instance;
	
	InneractiveSampleDisplayable displayable = new InneractiveSampleDisplayable();
	
	public InneractiveSampleMidlet() {
		instance = this;
		instance.displayable.setTitle("Sample app - Ads by inneractive");
	}

	public void startApp() {			
		Display.getDisplay(instance).setCurrent(displayable);
	}

	public void pauseApp() {
		
	}

	public void destroyApp(boolean unconditional) {
	}

	public static void quitApp() {	
		try {
			
			instance.destroyApp(true);
			instance.notifyDestroyed();
		}
		catch (Exception ex) {			
			ex.printStackTrace();
		}
	}
	

	public static void staticPauseApp(){
		instance.pauseApp();
	}

	/* (non-Javadoc)
	 * @see InneractiveSDK.InneractiveAdEventsListener#inneractiveOnClickAd(InneractiveSDK.IADView)
	 */
	public void inneractiveOnClickAd() {
		System.out.println("inneractive sample application - inneractiveOnClickAd event occurs");
		
	}
	/* (non-Javadoc)
	 * @see InneractiveSDK.InneractiveAdEventsListener#inneractiveOnSkipAd(InneractiveSDK.IADView)
	 */
	public void inneractiveOnSkipAd() {
		System.out.println("inneractive sample application - inneractiveOnSkipAd event occurs");
		
	}


	/* (non-Javadoc)
	 * @see InneractiveSDK.InneractiveAdEventsListener#inneractiveOnFailedToReceiveAd(InneractiveSDK.IADView)
	 */
	public void inneractiveOnFailedToReceiveAd() {
		System.out.println("inneractive sample application - inneractiveOnFailedToReceiveAd event occurs");
		
	}

	/* (non-Javadoc)
	 * @see InneractiveSDK.InneractiveAdEventsListener#inneractiveOnReceiveAd(InneractiveSDK.IADView)
	 */
	public void inneractiveOnReceiveAd() {
		System.out.println("inneractive sample application - inneractiveOnReceiveAd event occurs");
		
	}

	/* (non-Javadoc)
	 * @see InneractiveSDK.InneractiveAdEventsListener#inneractiveOnReceiveDefaultAd(InneractiveSDK.IADView)
	 */
	public void inneractiveOnReceiveDefaultAd() {
		System.out.println("inneractive sample application - inneractiveOnReceiveDefaultAd event occurs");
		
	}

}
