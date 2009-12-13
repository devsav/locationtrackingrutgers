/**
 * Recorder.java - This is the class that formats the captured data and prepares the file.
 * @author Muralikrishna K
 * @version 1.0
 * @see LocationListener
 * 
 */

package Project.GetLocation;

import java.util.Calendar;

import Project.GetLocation.FileParser;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.location.Location;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Recorder 
	{
	
    	private Location recorderLocation;
        private FileParser fp;
        private Calendar cal;
        private String newLine="\r\n";
        private String TelephoneNumber;
        private String CommunicationType;
        private ContextWrapper mContextWrapper;
        private userDetailManager UDM;
        private SendData SD;
        @SuppressWarnings("unused")
		private String SendDataStatus;
        private String Link="http://192.168.1.14:3000/";
        
        
 
        
        /**
    	 * Default Constructor that must never be used to initialize the object. 
    	 * @see LocationListener(ContextWrapper Atemp)
    	 */ 
        @SuppressWarnings("unused")
		private Recorder()
        {
        	
        }
    	/**
    	 * The default constructor for the Recorder Class.
    	 * <I>Precondition: The ContextWrapper class passed as the parameter must already have its onCreate Function executed.</I>
    	 * @param ContextWrapper Atemp The calling ContextWrapper Class.
    	 * @see ContextWrapper
    	 */
        Recorder(ContextWrapper Atemp)
        {
        	mContextWrapper=Atemp;
        	cal = Calendar.getInstance();
        	prepareFile();
        	getTelephoneNumber();
        	CommunicationType="Manual_Fix";
        	UDM=new userDetailManager(mContextWrapper);
        	
        }
       /**
  	    * This method records the location details to the file using the location details passed as the parameter.
  	    * @param loctemp Location Location type object to be recorded
  	    */   
    	Recorder(Location loctemp, Activity Atemp)
    	{
    		mContextWrapper=Atemp;
    		cal = Calendar.getInstance();
    		setRecorderLocation(loctemp);
    		prepareFile();
    		getTelephoneNumber();
    	}
        /**
    	   * This method sets the location of the object using the location details passed as the parameter.
    	   * @param loctemp Location The location that must be assigned to the object.
    	   */   	
    	public void setRecorderLocation(Location loctemp)
    	{
    		recorderLocation = loctemp;
    	}
        /**
    	   * This method sets up the fileparser object that the location details will be recorded to.
    	   */   
    	private void prepareFile()
    	{
            fp=new FileParser(mContextWrapper);
    	}

    	/**
 	      * This method records the location detail passed as parameter to the file.
          * @param loctemp Location The location that must be assigned to the object.
 	      */  
    	public void recordToFile(Location loctemp, String CommType)
   		{
   			setRecorderLocation(loctemp);
   			setCommunicationType(CommType);
   			recordToFile();
   		}
       /**
  	      * This method records the location detail passed as parameter to the file.
          * @param loctemp Location The location that must be assigned to the object.
  	      */  
    	public void recordToFile(Location loctemp)
    	{
    		setRecorderLocation(loctemp);
    		recordToFile();
    	}
       /**
 	      * This method records the location details of the object to the file.
 	      */ 
    	private void recordToFile()
    	{	
    		
    		writeString(TelephoneNumber+"/"+recorderLocation.getLatitude()+"/"+recorderLocation.getLongitude()
    				+"/"+cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+(cal.get(Calendar.DAY_OF_MONTH))
    				+"/"+cal.get(Calendar.HOUR_OF_DAY)+"/"+cal.get(Calendar.MINUTE)+"/"+CommunicationType);

    	}

    	/**
 	      * This method records the location detail passed as parameter to the file.
          * @param loctemp Location The location that must be assigned to the object.
 	      */  
    	public void setCommunicationType(String CommType)
   		{
   			CommunicationType = CommType;
   		}
    	/**
    	 * 
    	 * @param i int Used to Set the type of Communication.
    	 * 			1: Sets CommunicationType to "Incoming Call"
    	 * 			2: Sets CommunicationType to "Incoming Message"
    	 */
    			
    	public void setCommunicationType(int i)
    	{
    		switch(i)
    		{
    		case 1: CommunicationType="Incoming_Call"; break;
    		case 2: CommunicationType="Incoming_Message"; break;
    		}
    			
    	}
    	
    	
    	/**
	      * This method gets the Telephone number of the device.
	      */     	
    	private void getTelephoneNumber()
    	{
    		TelephonyManager mTelephonyMgr = (TelephonyManager) mContextWrapper.getSystemService(Context.TELEPHONY_SERVICE); 
    		TelephoneNumber = mTelephonyMgr.getLine1Number();
    	}
    	
    	
        /**
	      * This method writes the string in the parameter in the file.
	      * @param s String The string to be written to the file.
	      */    	
    	private void writeString(String s)
    	{
            fp.writeToFile(s+newLine);
    	}
    	/**
    	 * This method transmits the aggregated Location Data stored on the phone.
    	 */
    	public void sendFileData()
    	{
    		try
    		{
    		String formattedData;
    		UDM=new userDetailManager(mContextWrapper);
    		SD=new SendData();
    		fp.readFromFile();
    		Log.d("Recorder",""+(fp.getTotalInputEntries()));
    		for(int i=0;i<=fp.getTotalInputEntries();i++)
    			
    		{
    			formattedData=Link+"locations/mobile/"+UDM.getUsername()+"/"+UDM.getPassword()+"/"+fp.returnInputEntryIndex(i);
    			Log.d("Recorder",formattedData);
    			SD.getUrlData(formattedData);
    			fp.setEntryProcessed(i);
    			Log.d("Recorder input file index:",i+" ");
    		}
    		fp.cleanProcessedEntries();
    		}
    		catch (Exception e)
    		{
    			Log.d("Recorder", e.toString());
    		}
    	}
    	/**
    	 * This method sends the location data for a single capture.
    	 * @param loc Location The Location data which is sent.
    	 */
    	public void sendData(Location loc)
    	{
    		try
    		{
    			SD=new SendData();
    			String formattedData;
    			formattedData=Link+"locations/mobile/"+UDM.getUsername()+"/"+UDM.getPassword()+"/"
    			+TelephoneNumber+"/"+recorderLocation.getLatitude()+"/"+recorderLocation.getLongitude()
    			+"/"+cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+(cal.get(Calendar.DAY_OF_MONTH))
    			+"/"+cal.get(Calendar.HOUR_OF_DAY)+"/"+cal.get(Calendar.MINUTE)+"/"+CommunicationType;
    			Log.d("Recorder",formattedData);
    			@SuppressWarnings("unused")
				String response=SD.getUrlData(formattedData);
    			sendFileData();
    			
    		}
    		catch (Exception e)
    		{
    			recordToFile(loc);
    			Log.d("Recorder",e.toString());
    		}
    	}
    }


