/**
 * LocationService.java - This is the background server that captures Location Data depending on Cell Phone State.
 * * @author Muralikrishna K
* @version 1.0
* @see Service
 */

package Project.GetLocation;



import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class LocationService extends Service{
	public static final String TAG = "Location Service";
	private Recorder Rec;
	private GPSLocationListener gpll;
	
	private Location loc;
	
	private boolean DataCaptured=false;
	PhoneStateListener myPSL = null;
	TelephonyManager tm1;
	
    /**
	   * This method is executed as soon at the object is created.
	   * @param savedInstanceState Used if a saved instanced state needs reloading
	   */	

	public void onCreate() {
        super.onCreate();
        Log.d(TAG,TAG+"Started");
        startService();
        }

	/**
	 * This method registers the events that the service listens to.
	 */
	private void startService()
	{
		tm1 = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
          tm1.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);    
	}
	
	
	/**
	 * This method is executed when the service intercepts an incoming call.
	 */
	private PhoneStateListener mPhoneListener = new PhoneStateListener()
    {
            @Override
            public void onCallStateChanged(int state, String incomingNumber)
            {

            	try 
                   {
            		switch(state)
            			{
            			case TelephonyManager.CALL_STATE_RINGING:
            				if(!DataCaptured)
            				{
            					captureLocation("Incoming_call_from_"+incomingNumber); break;
            				}
            			case TelephonyManager.CALL_STATE_IDLE:
            				transmitData();break;
            				
            				
            			}
            		}
                catch (Exception e)
                   {
                   	Log.d(TAG,TAG+e);
                   }
            }
            };
	/**
	 * This method captures the location data and writes it to file.
	 */
    private void captureLocation(String commType)
    {
        Rec = new Recorder(this);
        gpll = new GPSLocationListener(this);
        
        loc = gpll.getCurrentLocation();
 	  	Rec.setCommunicationType(commType);
 	  	
 	  	Rec.recordToFile(loc);

 	  	DataCaptured=true;
    }
    /**
     * This method transmits the location data that were captured.
     */
    private void transmitData()
    {
    	if(DataCaptured)
    	{
    		Rec = new Recorder(this);
 	  		Rec.sendFileData();
 	  		DataCaptured=false;
    	}
    	
    }
    /**
     * Auto Generated.
     */
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * This method tell the service to stop listening the phone state changes.
	 */
    @Override 
    public void onDestroy() { 
    	tm1.listen(mPhoneListener, PhoneStateListener.LISTEN_NONE); 
    } 

}
