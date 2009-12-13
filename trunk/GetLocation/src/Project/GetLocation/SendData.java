package Project.GetLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
/**
* SendData.java - This is the class that sends the data over the internet to a specified URL. 
* @author Muralikrishna K
* @version 1.0
* 
*/
import android.util.Log;

public class SendData {
	
	public static final int connectionTimeout = 5000;
	/**
	 * 	This static method returns HTTPresponse type object for the input URL.
	 * @param url String The URL to be queried.
	 * @return HTTPresponse The HTTP response received
	 */
	public static InputStream getInputStreamFromUrl(String url) {  
		  InputStream content = null;  
		  try {  
		    HttpClient httpclient = new DefaultHttpClient();  
		    HttpResponse response = httpclient.execute(new HttpGet(url));  
		    content = response.getEntity().getContent();  
		  } catch (Exception e) {  
		    Log.d("[GET REQUEST]", "Network exception", e);  
		  }  
		    return content;  
		}  
	/**
	 * This method queries the URL provided using HTTP Get Request.
	 * @param url String The URL to be queried.
	 * @return String The response converted to a string format.
	 */
	public String getUrlData(String url) {
		        String websiteData = null;
		        try {
		        	HttpParams my_httpParams = new BasicHttpParams(); 
                    HttpConnectionParams.setConnectionTimeout(my_httpParams, connectionTimeout); 
                    HttpConnectionParams.setSoTimeout(my_httpParams, connectionTimeout); 
		            DefaultHttpClient client = new DefaultHttpClient(my_httpParams);

		            URI uri = new URI(url);
		            HttpGet method = new HttpGet(uri);
		            HttpResponse res = client.execute(method);
		            if(res!=null)
		            {
		            InputStream data = res.getEntity().getContent();
		            websiteData = generateString(data);
		            }
		       } catch (ClientProtocolException e) {
		           // TODO Auto-generated catch block
		           e.printStackTrace();
			       Log.d("SendData",e.toString());
		       } catch (IOException e) {
		         // TODO Auto-generated catch block
		           e.printStackTrace();
			       Log.d("SendData",e.toString());
		       } catch (URISyntaxException e) {
		           // TODO Auto-generated catch block
		           e.printStackTrace();
			       Log.d("SendData",e.toString());
		       }
		       Log.d("SendData",websiteData);
		       return websiteData;
		   }
	
	/**
	 * This method converts an InputStream Object to a string.
	 * @param stream InputStream The input InputStream object.
	 * @return String The converted String.
	 */
	public String generateString(InputStream stream) {
		        InputStreamReader reader = new InputStreamReader(stream);
		        BufferedReader buffer = new BufferedReader(reader);
		        StringBuilder sb = new StringBuilder();
		   
		        try {
		            String cur;
		            while ((cur = buffer.readLine()) != null) {
		                sb.append(cur + "\n");
		           }
		       } catch (IOException e) {
		           // TODO Auto-generated catch block
		           e.printStackTrace();
		       }
		  
		       try {
		           stream.close();
		       } catch (IOException e) {
		           // TODO Auto-generated catch block
		           e.printStackTrace();
		       }
		       return sb.toString();
		   }

}
