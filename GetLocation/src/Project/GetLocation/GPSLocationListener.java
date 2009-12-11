/**
* GPSLocationListener.java - This is the class that captures the GPS Location of the Device. 
* @author Muralikrishna K
* @version 1.0
* @see LocationListener
* 
*/

package Project.GetLocation;

import java.util.Calendar;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.content.ContextWrapper;


public class GPSLocationListener implements LocationListener{
	
	private Location loc;
	private Exception egps;
	private Calendar c;
	ContextWrapper mContextWrapper;

	/**
	 * Default Constructor that must never be used to initialize the object. 
	 * @see LocationListener(ContextWrapper Atemp)
	 */
	@SuppressWarnings("unused")
	private GPSLocationListener()
	{
	}
	/**
	 * The default constructor for the GPSLocationListener Class.
	 * <I>Precondition: The ContextWrapper class passed as the parameter must already have its onCreate Function executed.</I>
	 * @param ContextWrapper Atemp The calling ContextWrapper Class.
	 * @see ContextWrapper
	 */
	public GPSLocationListener(ContextWrapper Atemp)
	{
		mContextWrapper=Atemp;
		c=Calendar.getInstance();
		getFix();
	}

	/**
	 * This function gets the latest GPS co-ordinates of the device.
	 * @return boolean returns True if the GPS location was successfully captured, otherwise returns False.
	 * @see boolean
	 */
	public boolean getFix() 
	{
    	try
    	{
    		LocationManager lm = (LocationManager)mContextWrapper.getSystemService(Context.LOCATION_SERVICE);
       		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);        
       		loc = lm.getLastKnownLocation("gps");
       		c=Calendar.getInstance();
       		return true;
       	}
    	catch (Exception e)
    	{
    		egps=e;
    		return false;
    	}
    }
	/**
	 * This function returns the Location data that was last captured.
	 * @return Location The location data that is currently present in the class.
	 */
	 
	public Location getCurrentLocation()
	{
		return loc;
	}

	/**
	 * Returns the exception details.
	 * @return Exception The Exception Details.
	 * @pre getFix()=false.
	 */
	public Exception getException()
	{
		return egps;
	}
	
	/**
	 * Returns the time of the latest Fix obtained. This time will be the time on the device when the location data was captured.
	 * @return Calendar Calendar object that contains the details of the Fix.
	 */
	public Calendar getFixTime()
	{
		return c;
	}
    	/**
  	   * This method is invoked whenever there is a change in location.
  	   * @param loc1 Location object that contains the updated location details.
  	   */
        @Override
        public void onLocationChanged(Location loc1) {
            // TODO Auto-generated method stub
        }
        
    	/**
    	   * This method is invoked whenever the GPS provider is disabled.
    	   * @param provider String object that contains the name of the provider.
    	   */
        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

    	/**
  	   * This method is invoked whenever the GPS provider is enabled.
  	   * @param provider String object that contains the name of the provider.
  	   */
        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

    	/**
  	   * This method is invoked whenever there is a change in the GPS status.
  	   * @param provider String object that contains the name of the provider.
  	   * @param status int object that contains the GPS status.
  	   * @param extras Bundle type object containing additional information.
  	   */
        @Override
        public void onStatusChanged(String provider, int status, 
            Bundle extras) {
            // TODO Auto-generated method stub
        }
}
