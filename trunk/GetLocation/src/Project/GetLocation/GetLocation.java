package Project.GetLocation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import android.location.LocationListener;


import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetLocation extends Activity {
	private TextView mLocationDisplay;
    private Button mGetLocation;

    private int mYear;
    private int mMonth;
    private int mDay;
    private Location loc;
    private StringBuilder displayText;
    private Recorder Rec;



    /**
	   * This method is executed as soon at the object is created.
	   * @param savedInstanceState Used if a saved instanced state needs reloading
	   */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      
       

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        Rec = new Recorder();
        
        getFix();
        
        

        
        mLocationDisplay = (TextView) findViewById(R.id.locationDisplay);
        mGetLocation = (Button) findViewById(R.id.getLocation);
 
        updateDisplay();
        
        mGetLocation.setOnClickListener(new View.OnClickListener() {
                  public void onClick(View v) {
                   	getFix();
                    updateDisplay();
                   }
               });  
    }
    
    
    /**
	   * This method refreshes the display.
	   */
    private void updateDisplay() {
    	mLocationDisplay.setText(displayText); 	
    }
    
    /**
	   * This method gets the latest GPS co-ordinates.
	   */    
    private void getFix() {
    	try
    	{
          LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
          lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyLocationListener());        
          loc = lm.getLastKnownLocation("gps");

          displayText=new StringBuilder()
          // Month is 0 based so add 1
          .append(mMonth + 1).append("-")
          .append(mDay).append("-")
          .append(mYear).append(" ")
          .append("\n").append(loc.getLatitude())
          .append(" ").append(loc.getLongitude());
          
          Rec.recordToFile(loc);
    	}
    	catch (Exception e){
    		displayText=new StringBuilder()
            // Month is 0 based so add 1
            .append(mMonth + 1).append("-")
            .append(mDay).append("-")
            .append(mYear).append(" ")
            .append("\n").append("Unable to get GPS Fix:").append(e);
    		
     	}
     }
    private class MyLocationListener implements LocationListener 
    {
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
   
    
    public class Recorder
    {
    	private String recordText;
    	private Location recorderLocation;
    	private String errorStatus;
        private FileParser fp;
        private String IMSI;
        private String IMEI;
        private Calendar cal;
        private String newLine="\r\n";
        private String tabChar="\t";
        
        Recorder()
        {
        	cal = Calendar.getInstance();
        	prepareFile();
        	getIMEI();
        }
    	
    	Recorder(Location loctemp)
    	{
    		cal = Calendar.getInstance();
    		setRecorderLocation(loctemp);
    		prepareFile();
    		getIMEI();
    	}
    	
    	public void setRecorderLocation(Location loctemp)
    	{
    		recorderLocation = loctemp;
    	}
    	
    	private void prepareFile()
    	{
            fp=new FileParser();
    	}
    	
    	public void recordToFile(Location loctemp)
    	{
    		setRecorderLocation(loctemp);
    		recordToFile();
    	}
    	
    	private void recordToFile()
    	{
    		writeString("<LocationDetails>");
    		
    		writeString(tabChar+"<IMEI>"+IMEI+"</IMEI>");
    		
       		writeString(tabChar+"<Date>");
       		writeString(tabChar+tabChar+"<Year>"+cal.get(Calendar.YEAR)+"</Year>");
       		writeString(tabChar+tabChar+"<Month>"+(cal.get(Calendar.MONTH)+1)+"</Month>");
       		writeString(tabChar+tabChar+"<Day>"+cal.get(Calendar.DAY_OF_MONTH)+"</Day>");
       		writeString(tabChar+tabChar+"<Hour>"+cal.get(Calendar.HOUR_OF_DAY)+"</Hour>");
       		writeString(tabChar+tabChar+"<Minute>"+cal.get(Calendar.MINUTE)+"</Minute>");
       		writeString(tabChar+tabChar+"<Second>"+cal.get(Calendar.SECOND)+"</Second>");
       		writeString(tabChar+"</Date>");
       		
       		writeString(tabChar+"<Location>");
       		writeString(tabChar+tabChar+"<Latitude>"+recorderLocation.getLatitude()+"</Latitude>");
       		writeString(tabChar+tabChar+"<Longitude>"+recorderLocation.getLongitude()+"</Longitude>");
       		writeString(tabChar+"</Location>");
       		
       		writeString("</LocationDetails>");
    	}
    	
    	private void getIMEI()
    	{
    		TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); 
    		IMSI = mTelephonyMgr.getSubscriberId(); 
    		IMEI = mTelephonyMgr.getDeviceId();
    	}
    	
    	private void writeString(String s)
    	{
            fp.writeToFile(s+newLine);
    	}
    }

    public class FileParser
    {

    	private String InputString;
    	private String OutputString;
    	private FileInputStream fIn;
    	private InputStreamReader isr;
    	private FileOutputStream fOut;
    	private OutputStreamWriter osw;
    	private StringBuilder ErrorStatus;
    	private String FileName = "LocationData.xml";
    	
    	FileParser()
    	{
 //   		writeToFile("<Data>XML Location Data<Data>");
    	}
    	
    	public void writeToFile(String s)
    	{
    		try
			{
				fOut = openFileOutput(FileName, MODE_PRIVATE|MODE_APPEND);
				osw = new OutputStreamWriter(fOut);
				osw.write(s);
				osw.flush();
			}
			catch (Exception e1)
			{
				ErrorStatus = new StringBuilder().append(e1);
			}
    	}
    	
    	public void readFromFile()
    	{
    		try
    		{
    			fIn = openFileInput(FileName);
    			isr = new InputStreamReader(fIn);
    			char[] inputBuffer = new char[25];
    			isr.read(inputBuffer);
    			InputString = new String(inputBuffer);
    			
    		}
    		
    		catch (Exception e1)
    		{
    			ErrorStatus = new StringBuilder().append(e1);
    		}
    	}
    }
    
}