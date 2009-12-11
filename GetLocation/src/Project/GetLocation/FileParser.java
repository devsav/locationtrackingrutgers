/**
 * FileParser.java - This is the class that reads from/writes to the file "LocationData.dat".
 * @author Muralikrishna K
 * @version 1.0
 * @see LocationListener
 * 
 */

package Project.GetLocation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

public class FileParser {
	

    	private String[] InputString;
    	private FileOutputStream fOut;
    	private OutputStreamWriter osw;
    	private String FileName = "LocationData.dat";
    	private ContextWrapper mContextWrapper;
    	private boolean Error=false;
    	private boolean ReadModeOpen=false;
    	private boolean AppendModeOpen=false;
    	private boolean CleanModeOpen=false;
    	private String ErrorType;
    	private Exception ErrorException;
    	private int InputIndex=0;
    	private int TotalInputEntries=0;

    	
        /**
    	 * Default Constructor that must never be used to initialize the object. 
    	 * @see LocationListener(ContextWrapper Atemp)
    	 */ 
    	@SuppressWarnings("unused")
		private FileParser()
    	{

    	}
    	
    	
    	/**
    	 * The default constructor for the Recorder Class.
    	 * <I>Precondition: The ContextWrapper class passed as the parameter must already have its onCreate Function executed.</I>
    	 * @param ContextWrapper Atemp The calling ContextWrapper Class.
    	 * @see ContextWrapper
    	 */
    	public FileParser(ContextWrapper Atemp)
    	{
    		mContextWrapper=Atemp;
    	}
    	
    	
   	
        /**
	      * This method writes the string in the parameter in the file.
	      * @param s String The string to be written to the file.
	      */    	    	
    	public void writeToFile(String s)
    	{
    		try
			{
				fOut = mContextWrapper.openFileOutput(FileName, Context.MODE_PRIVATE|Context.MODE_APPEND);
				osw = new OutputStreamWriter(fOut);
				Error=false;
				AppendModeOpen=true;
				
				osw.write(s);
				osw.flush();

				Error=false;
				
				osw.flush();
				osw.close();
				fOut.close();
				
			}
			catch (Exception e)
			{
				Error=true;
				ErrorException=e;
				Log.d("File Parser",e.toString());
			}
			ErrorType="Output File Append";
			
			closeFile();
    	}

    	
    	/**
    	 * This function opens the file in write-append mode.
    	 * @return boolean True if file open is successful, false if not.
    	 */
    	private boolean openFileToRead()
    	{
    		return ReadModeOpen;
    	}
    	
    	
    	/**
	      * This method reads all the file contents and closes it.
	      */    	
    	public void readFromFile()
    	{
    		FileInputStream fIn;
    		InputStreamReader isr;
    		BufferedReader bIn;
    		try
    		{
    		fIn = mContextWrapper.openFileInput(FileName);
    		isr = new InputStreamReader(fIn);
    		bIn = new BufferedReader(isr);
			Error=false;
			ReadModeOpen=true;
			
			ErrorType="File open Read mode";
    		
    		try
    		{
    			if(!ReadModeOpen)
				{
					if(!openFileToRead())
					{
						throw ErrorException;
					}
				}
    			InputString = new String[50];
    			InputIndex = 0;
    			
    			while((InputString[InputIndex]=new String(bIn.readLine()))!=null)
    			{
    			InputIndex++;
    			Error=false;
    			}
    			

    		}
    		
    		catch (Exception e)
    		{
    			Error=true;
    			ErrorException=e;
				Log.d("File Parser file read",InputIndex+e.toString());
    		}
    		ErrorType="File Read";
    		TotalInputEntries=InputIndex-1;
			bIn.close();
			isr.close();
			fIn.close(); 
    		}
    		catch (Exception e)
    		{
    			Log.d("File Parser file read",e.toString());
    		}
    	}
    	
    	
    	/**
    	 * This function returns a particular entry in file.
    	 * @param i int The file entry to be returned.
    	 * @return String The ith entry of file if 1<i<=getTotalFileEntries(). Otherwise returns null.
    	 */
    	
    	public String returnInputEntryIndex(int i)
    	{
    		if(i<0||i>TotalInputEntries)
    		{
    			return null;
    		}
    		else
    		{
    			return InputString[i-1];
    		}
    	}
    	
    	public int getTotalInputEntries()
    	{
    		return TotalInputEntries;
    	}
    	/**
    	 * 
    	 * @param i int The entry of file to be set as processed
    	 * @return boolean True if the entry has been set as processed, else false.
    	 */
    	public boolean setEntryProcessed(int i)
    	{
    		if(i<0||i>TotalInputEntries)
    		{
    			return false;
    		}
    		else
    		{
    			InputString[i]="Done";
    			return true;
    		}
    	}
    	
    	/**
    	 * This function cleans the file of any entries that have been processed.
    	 * @return boolean True if the file has been cleaned of the processed entries, otherwise false.
    	 */
    			
    	public boolean cleanProcessedEntries()
    	{

    			if((ReadModeOpen|AppendModeOpen))
    			{	
    				
    			    Error=true;
    				ErrorType="File already open Read/Append mode";
    				CleanModeOpen=false;
    			}
    			else
    			{
    				try
    				{
    					fOut = mContextWrapper.openFileOutput(FileName, Context.MODE_PRIVATE);
    					osw = new OutputStreamWriter(fOut);
    					Error=false;
    					CleanModeOpen=true;
    					for(int i=0;InputString[i]!=null;i++)
    					{
    						if(InputString[i]!="Done")
    						{
    							osw.write(InputString[i]);
    							osw.flush();
    							Log.d("File Parser",InputString[i]);
    						}
    					}
    					closeFile();
    				}
    				catch (Exception e)
    				{
    					Error=true;
    					ErrorException=e;
    					CleanModeOpen=false;
    					Log.d("File Parser",e.toString());
    				}
    				ErrorType="File process Clean mode";

    			}
    		
    		return CleanModeOpen;
    	}
        /**
         * This function closes the file.
         * @return boolean True if File has closed Successfully, otherwise False.
         */
    	public boolean closeFile()
    	{
    		try
    		{
    			if(ReadModeOpen)
    			{

    				
    			}
    			if(AppendModeOpen)
    			{

    				
    			}
    			
    			if(CleanModeOpen)
    			{

    				osw.flush();
    				osw.close();
    				fOut.close();
    			}
    			
    			return true;
    		}
    		catch (Exception e)
    		{
    			Error=true;
    			ErrorException=e;
    			return false;
    		}
    	}
    	/**
    	 * This function returns the error status of the object.
    	 * @return boolean True if any error ,otherwise false.
    	 */
    	public boolean getErrorStatus()
    	{
    		return Error;
    	}
    	
    	
    	/**
    	 * This function returns any exception that is generated by the object.
    	 * @return Exception The exception generated within the object
    	 */
    	public Exception getErrorDetails()
    	{
    		return ErrorException;
    	}
    	
    	
    	/**
    	 * This function returns a string that describes the latest error type.
    	 * @return String Returns the latest error type. 
    	 * <I>This is returned regardless of whether an error has been generated or not.</I>
    	 */
    	public String getErrorType()
    	{
    		return ErrorType+" Error.";
    	}
    	
}
