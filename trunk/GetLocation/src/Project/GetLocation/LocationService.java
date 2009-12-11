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
	
    /**
	   * This method is executed as soon at the object is created.
	   * @param savedInstanceState Used if a saved instanced state needs reloading
	   */	
	PhoneStateListener myPSL = null;
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
		TelephonyManager tm1 = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
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
            			if(TelephonyManager.CALL_STATE_RINGING==1)
            			{
                            captureLocation("Incoming_call_from_"+incomingNumber);
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
 	  	Rec.recordToFile(loc, commType);
 	  	Rec.sendData();
    }
    /**
     * Auto Generated.
     */
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
