/**
* userDetailManager.java - This is the class that captures, encodes and decodes the user name and password.
* @author Muralikrishna K
* @version 1.0
* 
*/

package Project.GetLocation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.content.ContextWrapper;

public class userDetailManager {
	

	@SuppressWarnings("unused")
	private String InputString;
	private FileInputStream fIn;
	private InputStreamReader isr;
	private FileOutputStream fOut;
	private OutputStreamWriter osw;
	private String FileName = "UserDetail.dat";
	private ContextWrapper A;
	private boolean Error;
	private String ErrorType;
	private Exception ErrorException;
	private String Password=" ";
	private String Username=" ";
	private String Encodedstring;
    /**
	 * Default Constructor that must never be used to initialize the object. 
	 */ 
	@SuppressWarnings("unused")
	private userDetailManager()
	{

	}
	/**
	 * The default constructor for the user Detail Manager Class.
	 * <I>Precondition: The ContextWrapper class passed as the parameter must already have its onCreate Function executed.</I>
	 * @param ContextWrapper Atemp The calling ContextWrapper Class.
	 * @see ContextWrapper
	 */
	public userDetailManager(ContextWrapper Atemp)
	{
		A=Atemp;
		readFromFile();
	}
	
	/**
	 * Sets the username and password of the user.
	 * @param Uname String Username
	 * @param Pword String Password
	 */
	public void setUsernamePassword(String Uname, String Pword)
	{
		Username=Uname;
		Password=Pword;
		encode();
	}
	
	/**
	 * Returns the user name.
	 * @return String
	 */
	public String getUsername()
	{
		return Username;
	}
	
	/**
	 * Returns the password.
	 * @return
	 */
	public String getPassword()
	{
		return Password;
	}
	
	/**
	 * Encodes the username and password and saves it to file.
	 */
	private void encode()
	{
		Encodedstring=new String(Username+","+Password);
		writeToFile(Encodedstring);
	}

	
	/**
	 * This method decodes the encoded string into user name and password.
	 */
	private void decode()
	{
		Username=Encodedstring.substring(0, Encodedstring.indexOf(','));
		Password=Encodedstring.substring(Encodedstring.indexOf(',')+1,Encodedstring.indexOf('\0'));
	}

	
	/**
      * This method writes the string in the parameter in the file.
      * @param s String The string to be written to the file.
      */    
	private void writeToFile(String s)
	{
		try
		{
			fOut = A.openFileOutput(FileName, Context.MODE_PRIVATE);
			osw = new OutputStreamWriter(fOut);
			osw.write(s);
			osw.flush();
			osw.close();
			fOut.close();
			Error=false;
			readFromFile();
		}
		catch (Exception e)
		{
			Error=true;
			ErrorException=e;
		}
		ErrorType="User data modify error";		
	}
	
	
    /**
      * This method reads a line from the file into the InputString variable.
      */    	
	private void readFromFile()
	{
		try
		{
			fIn = A.openFileInput(FileName);
			isr = new InputStreamReader(fIn);
			char[] inputBuffer = new char[25];
			isr.read(inputBuffer);
			Encodedstring = new String(inputBuffer);
			Error=false;
			isr.close();
			fIn.close();
			decode();
		}
		
		catch (Exception e)
		{
			Error=true;
			ErrorException=e;
		}
		ErrorType="User data read error";
	}

	
	/**
	 * This method returns the error status of the object.
	 * @return True if object is in error, False if otherwise.
	 */
	public boolean getErrorStatus()
	{
		return Error;
	}
	
	
	/**
	 * This method returns the exception details of the object.
	 * @return Exception
	 */
	public Exception getErrorDetails()
	{
		return ErrorException;
	}
	
	
	/**
	 * This method returns the ErrotType of the object. Returns a valid error type regardless of whether there is an error or not.
	 * @return String ErrorType
	 */
	public String getErrorType()
	{
		return ErrorType;
	}
}
