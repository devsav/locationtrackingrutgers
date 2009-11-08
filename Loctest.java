//Software Package Imported
package example.loctest;

//Libraries imported
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.location.*;
 
 
public class Loctest extends MIDlet implements CommandListener
{
   private Display display;   
   private Form form;   
   private Command cmdExit,cmdOK;  
   private StringItem si;         
 
   public Loctest()
   {
//Create a simple UI
      display = Display.getDisplay(this); 
      form = new Form("Location Api test");
      cmdExit = new Command("Exit",Command.EXIT,5);  
      cmdOK = new Command("OK",Command.OK,1);  
      si = new StringItem("Geo Location", "Click OK");  
      form.append(si); 
      form.addCommand(cmdOK);
      form.addCommand(cmdExit);  
      form.setCommandListener(this);         
   }
   //Nokia S60 Default requirement
   public void startApp()  
   {
      display.setCurrent(form);
   }
   //Nokia S60 Default requirement 
   public void pauseApp()  
   {
   }
   //Nokia S60 Default requirement
   public void destroyApp(boolean flag) 
   {
      notifyDestroyed();
   }
 //Nokia S60 Default requirement
   public void commandAction(Command c, Displayable d)
   {
      if (c == cmdOK){
         Retriever ret = new Retriever(this);  
         ret.start();
         } else if (c == cmdExit) {  
            destroyApp(false);  
      }  
   }
   //Nokia S60 Default requirement
   public void displayString(String string)
   {
      si.setText(string);  
   }
}
 
//Creating a thread for the Location Tracking
class Retriever extends Thread
{
   private Loctest midlet;
   public Retriever(Loctest midlet)
   {
      this.midlet = midlet;
   }
 
   public void run()
   {
      try 
      {
         checkLocation();
      } 
      catch (Exception ex)
      {
         ex.printStackTrace();  
         midlet.displayString(ex.toString());  
      }
   }
   //Get the Location Details using default packages and display it on the screen
   public void checkLocation() throws Exception
   {
      String string;  
      Location l;  
      LocationProvider lp;  
      Coordinates c;
 
      Criteria cr= new Criteria(); 
      cr.setHorizontalAccuracy(500);
 
      lp= LocationProvider.getInstance(cr);
      l = lp.getLocation(60);
 
      c = l.getQualifiedCoordinates();
      if(c != null ) {
         // Use coordinate information 
         double lat = c.getLatitude(); 
         double lon = c.getLongitude(); 
         string = "\nLatitude : " + lat + "\nLongitude : " + lon; 
       } else {
         string ="Location API failed";
       }
       midlet.displayString(string); 
   }
}