package client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import fvms.team.ejb.Person;

public class ClientTest {

static public void main(String[] args)
{
  try {
	HttpClient httpclient = new DefaultHttpClient();
//	HttpGet httpget = new HttpGet("http://runbetatest.com:8080/HttpGetServlet/hello");
	HttpGet httpget = new HttpGet("http://localhost:8080/HttpGetServlet/hello");
	HttpResponse response = httpclient.execute(httpget);
	HttpEntity entity = response.getEntity();
	if (entity != null) { 
	    InputStream instream = entity.getContent();
	    try {
	    	
	    	  InputStreamReader in= new InputStreamReader(instream);
	    	  BufferedReader bin= new BufferedReader(in);
	    	  
	    	  for( int i=0; i<1; i++)
	    	  {
		    	  String line = bin.readLine();
		    	  System.out.println(line);
		    	  
		    	  Gson gson = new Gson();
		    	  Person person = gson.fromJson(line, Person.class);
		    	  
		    	  System.out.println(person);
	    	  }
	        
	    } finally {
	        instream.close();
	    }
	}
  }catch(Exception e ) {
	  System.out.println("Client Error");
	  System.exit(-1); 
  }

}

}
