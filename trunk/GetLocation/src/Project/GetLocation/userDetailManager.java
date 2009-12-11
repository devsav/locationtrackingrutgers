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
	
	@SuppressWarnings("unused")
	private userDetailManager()
	{

	}
	
	public userDetailManager(ContextWrapper Atemp)
	{
		A=Atemp;
		readFromFile();
	}
	
	public void setUsernamePassword(String Uname, String Pword)
	{
		Username=Uname;
		Password=Pword;
		encode();
	}
	
	public String getUsername()
	{
		return Username;
	}
	
	public String getPassword()
	{
		return Password;
	}
	private void encode()
	{
		Encodedstring=new String(Username+","+Password);
		writeToFile(Encodedstring);
	}
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

	public boolean getErrorStatus()
	{
		return Error;
	}
	
	public Exception getErrorDetails()
	{
		return ErrorException;
	}
	
	public String getErrorType()
	{
		return ErrorType;
	}
}
