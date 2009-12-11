/**
* GetLocation.java - This is the key class that Implements the user interface for the Location Tracking Service. 
* @author Muralikrishna K
* @version 1.0
* @see Activity
* <P>
* It consists of the User Interface that interfaces with the background service that implements the Location Capture functionality.
* It can also track the location of the Cell-phone if the user requests so. This kind of a location capture will be labeled as Manual.
* </P>
* 
*/
package Project.GetLocation;

import java.util.Calendar;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GetLocation extends Activity {
	private TextView mLocationDisplay;
	private TextView mServiceStatusDisplay;
    private Button mGetManualLocation;
    private Button mStartService;
    private Button mStopService;
    private Button mUpdateUser;
    private EditText mUsername;
    private EditText mPassword;

    private Location loc;
    private StringBuilder displayText;
    private Recorder Rec;
    private GPSLocationListener gpll;
    private Calendar c;
    private StringBuilder Latitude;
    private StringBuilder Longitude;
    private userDetailManager UDM;
    public final static String TAG="Get Location";

    /**
	   * This method is executed as soon at the object is created.
	   * @param savedInstanceState Used if a saved instanced state needs reloading
	   */
    protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 

        // Initialize current time and date.
        c = Calendar.getInstance();

        Rec = new Recorder(this);
        gpll = new GPSLocationListener(this);
        UDM = new userDetailManager(this);
    	new SendData();
        
        // Set-up the Initial Display
        //setDisplayText();
    	displayText=new StringBuilder();
    	displayText.append("Press Manual Fix to get a Manual Fix");
        
        // Set up the User interface Elements
        mLocationDisplay = (TextView) findViewById(R.id.locationDisplay);
        mGetManualLocation = (Button) findViewById(R.id.getManualFix);
        mUsername = (EditText) findViewById(R.id.userName);
        mPassword = (EditText) findViewById(R.id.passWord);
        mServiceStatusDisplay = (TextView) findViewById(R.id.serviceStatusDisplay);
        mUpdateUser = (Button) findViewById(R.id.updateUser);
        
        // Add user name and Password to the display
        mUsername.setText(UDM.getUsername());
        mPassword.setText(UDM.getPassword());
        
        mStartService = (Button) findViewById(R.id.startService);
        mStopService = (Button) findViewById(R.id.endService);
        
        // Update the Display
        updateDisplay();
       
        // Set up the "Manual Fix" Button
        mGetManualLocation.setOnClickListener(new View.OnClickListener() {
                  public void onClick(View v) {
                   	setDisplayText();
                    updateDisplay();
                   }
               });
        // Set up the "Start Service" Button
        mStartService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startService();
            	mServiceStatusDisplay.setText("Service Started");
            }
        });
        // Set up the "End Service" Button
        mStopService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	stopService();
            	mServiceStatusDisplay.setText("Service Stopped");
            }
        });        
        // Set up the "Update User" Button
        mUpdateUser.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UDM.setUsernamePassword(mUsername.getText().toString(), mPassword.getText().toString());
		        Log.d(TAG, "User Details Updated");
			}
		});
    }
    
    
    /**
	   * This method refreshes the User Interface.
	   */
    private void updateDisplay() {
    	mLocationDisplay.setText(displayText); 	
    }
    
    
    /**
	   * This method updates the display text as per the current location data and its time of Capture.
	   */    
    private void setDisplayText()
    {
    	
    	displayText=new StringBuilder();
    	Latitude=new StringBuilder();
    	Longitude=new StringBuilder();

    	
    	
    	if(gpll.getFix())
    	{
    	
    	  loc = gpll.getCurrentLocation();
    	  c=gpll.getFixTime();
    	  // Month is 0 based so add 1
    	  
    	  if(loc.getLatitude()<0)
      		{
      			Latitude.append(-loc.getLatitude()).append(" S");
      		}
      		else
      		{
      			Latitude.append(loc.getLatitude()).append(" N");
      		}
      		if(loc.getLongitude()<0)
      		{
      			Longitude.append(-loc.getLongitude()).append(" W");
      		}
      		else
      		{
      			Longitude.append(loc.getLongitude()).append(" W");
      		}
    	  
          displayText.append(c.get(Calendar.MONTH) + 1).append("/")
          .append(c.get(Calendar.DAY_OF_MONTH)).append("/")
          .append(c.get(Calendar.YEAR)).append("   ")
          .append(c.get(Calendar.HOUR_OF_DAY)).append(":")
          .append(c.get(Calendar.MINUTE)).append(":")
          .append(c.get(Calendar.SECOND)).append(" ")
          .append("\n").append(Latitude.toString())
          .append(" ").append(Longitude.toString());
    	
          
          Rec.sendData(loc);
    	}
    	else
    	{

            // Month is 0 based so add 1
            displayText.append(c.get(Calendar.MONTH) + 1).append("/")
            .append(c.get(Calendar.DAY_OF_MONTH)).append("/")
            .append(c.get(Calendar.YEAR)).append("   ")
            .append(c.get(Calendar.HOUR_OF_DAY)).append(":")
            .append(c.get(Calendar.MINUTE)).append(":")
            .append(c.get(Calendar.SECOND)).append(" ")
            .append("\n").append("Unable to get GPS Fix:").append(gpll.getException());
    		
     	}
    	
     }
    
    
    /**
     * This method starts the background service Location Service.
     */
    private void startService() 
    {
        Intent serviceIntent = new Intent(GetLocation.this, LocationService.class);
        Log.d(TAG, "Start Location service");
        startService(serviceIntent);
    }

    
    /**
     * This method stops the background service Location Service.
     */
    private void stopService() 
    {

        Intent serviceIntent = new Intent(GetLocation.this, LocationService.class);
        Log.d(TAG, "Stop Location service");
        if (stopService(serviceIntent)) 
        {
        Log.d(TAG, "Service Stopped!");
        }
    }
        
}