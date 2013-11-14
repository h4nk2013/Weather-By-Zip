package com.example.weatheractivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;


public class JSONWeather extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String...params) {
		HttpURLConnection con;
		 InputStream is;
		 String newUrl = params[0];
			try {
				con = (HttpURLConnection) ( new URL(newUrl)).openConnection();
				con.setRequestMethod("POST");
			    con.setDoInput(true);
			    con.setDoOutput(true);
			    con.connect();
			    StringBuffer buffer = new StringBuffer();
		        is = con.getInputStream();
		        BufferedReader br = new BufferedReader(new InputStreamReader(is));
		        String line = null;
		        while (  (line = br.readLine()) != null )
		            buffer.append(line + "\r\n");		        
		        is.close();
		        con.disconnect();		        		        
		        return buffer.toString();			    		        
			} 
			catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	};
	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
