import javax.microedition.lcdui.*;

import java.io.*;
import java.util.Hashtable;
import java.util.Vector;
import InneractiveSDK.IADView;
import InneractiveSDK.IADView.IaOptionalParams;


public class InneractiveSampleDisplayable extends Form implements CommandListener {

	Worker worker;
	StringItem stHeader = new StringItem("", "");
	ImageItem imageItem1 = new ImageItem("", null, ImageItem.LAYOUT_DEFAULT, "");
	final static int IDLE = 0;
	final static int GET_BANNER_AD = 1;
	final static int DISPLAY_INTERSTITIAL_AD = 3;
	final static int EXIT = 4;
	final static int CLICK_THE_BANNER = 5;
	
	Vector Ad;


	class Worker extends Thread {

		boolean terminated = false;
		int mTask;
		InneractiveSampleDisplayable mOwner;

		public Worker( InneractiveSampleDisplayable owner ) {
			mOwner = owner;
		}


		synchronized public void run() {

			while (!terminated) {
				mTask = IDLE;
				mOwner.stHeader.setText( "Welcome to inneractive Ad sample" );
				try {
					wait();
				}
				catch (InterruptedException ex) {}

				mOwner.stHeader.setText( "please wait..." );

				try {
					switch (mTask) {
						case CLICK_THE_BANNER:
							if(null != Ad){
								String clickURL = (String)Ad.elementAt(1);
								if(null != clickURL && !clickURL.equals("")){
									boolean forceExit = InneractiveSampleMidlet.instance.platformRequest( clickURL );

									System.out.println("platformRequest - the midlet should be close? " + forceExit);

									if(forceExit){	
										InneractiveSampleMidlet.quitApp();
									}
								}
							}
							break;
							
						case DISPLAY_INTERSTITIAL_AD:
							System.out.println("IADView.displayInterstitialAd()" );
							Hashtable interstitialData = new Hashtable();
							interstitialData.put(IaOptionalParams.Key_Age, "30");
							interstitialData.put(IaOptionalParams.Key_Gender, "F");
							interstitialData.put(IaOptionalParams.Key_Gps_Location, "53.542132,-2.239856");
							interstitialData.put(IaOptionalParams.Key_Keywords, "Games");
							interstitialData.put(IaOptionalParams.Key_Location, "US");
							interstitialData.put(IaOptionalParams.Key_interstitial_GO_btn, "GO");
							interstitialData.put(IaOptionalParams.Key_interstitial_SKIP_btn, "SKIP");
							IADView.displayInterstitialAd(InneractiveSampleMidlet.instance, "MyCompany_MyApp" ,interstitialData, InneractiveSampleMidlet.instance);
							break;

						case GET_BANNER_AD:
							System.out.println("IADView.getBannerAd()" );
							Hashtable metaData = new Hashtable();
							
							metaData.put(IaOptionalParams.Key_Age, "30");
							metaData.put(IaOptionalParams.Key_Gender, "F");
							metaData.put(IaOptionalParams.Key_Gps_Location, "53.542132,-2.239856");
							metaData.put(IaOptionalParams.Key_Keywords, "Games");
							metaData.put(IaOptionalParams.Key_Location, "US");

							Ad = IADView.getBannerAdData(InneractiveSampleMidlet.instance, "MyCompany_MyApp", metaData);
							Image retImg = null;
							if (null != Ad){
								retImg = (Image)Ad.elementAt(0);
							}
							if (retImg != null){
								Image.createImage(retImg);
								imageItem1.setImage(retImg);
							}
							else{
								System.out.println("retImg is null");
							}
							
							
						
							break;
							
						case EXIT:
							InneractiveSampleMidlet.quitApp();
							break;
					}
				} catch (Exception e) {
					mOwner.append(e.getMessage());
				} catch (Throwable e) {
					mOwner.append(e.getMessage());
				}
			}
		}


		synchronized public boolean doTask( int task ) {
			if (mTask != IDLE) return false;
			mTask = task;
			notify();
			return true;
		}
	}



	public InneractiveSampleDisplayable() {
		super("Displayable Title");
		try {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		worker = new Worker( this );
		worker.start();
	}


	private void jbInit() throws Exception {
		setCommandListener(this);
		addCommand(new Command("banner", Command.CANCEL, 30));
		addCommand(new Command("interstitial", Command.STOP, 10));
		addCommand(new Command("Click the Banner", Command.BACK, 1));
		addCommand(new Command("Exit", Command.EXIT, 40));
		append(stHeader);
		append(imageItem1);

	}


	public void commandAction(Command command, Displayable displayable) {
			if (command.getCommandType() == Command.BACK)
				worker.doTask( CLICK_THE_BANNER );
			else if (command.getCommandType() == Command.STOP)
				worker.doTask( DISPLAY_INTERSTITIAL_AD );
			else if (command.getCommandType() == Command.CANCEL)
				worker.doTask( GET_BANNER_AD );
			else if (command.getCommandType() == Command.EXIT)
				worker.doTask( EXIT );
	}
	
	


}
