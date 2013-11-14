package com.example.weatheractivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.showlocationactivity.R;


public class WeatherActivity extends Activity implements LocationListener {
	
  public static final String PREFS_NAME = "MyPrefsFile";

  private LinearLayout l1;
  private TextView latituteField,longitudeField, day1, day2, day3, weather,location1, currentCondition, day4, day5, day6, day7, day8, day9;
  private TextView four, five, six, seven, eight, nine;
  private LocationManager locationManager;
  InputStream is = null;
  private JSONObject jObj;
  private JSONArray test1;
  private String city,weatherF, weatherC,provider,currentLoc,todayIcon,currentCondition2,no_of_days;
  private String[] high = new String[10];
  private String[] low = new String[10];
  private String[] conditions = new String[10];
  private String[] iconURL = new String[10];
  private ImageView imageView0, imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;
  private Bitmap currentImage=null;
  private Bitmap[] imageConditions = new Bitmap[10];
  private String flag;
  private int j;
  /** Called when the activity is first created. */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);    
    setContentView(R.layout.activity_weather);
    l1 = (LinearLayout) findViewById(R.id.linearLayout);
    location1 = (TextView) findViewById(R.id.TextView06);
    
    weather = (TextView) findViewById(R.id.weatherC);
    
    four = (TextView) findViewById(R.id.four);
    five = (TextView) findViewById(R.id.five);
    six = (TextView) findViewById(R.id.six);
    seven = (TextView) findViewById(R.id.seven);
    eight = (TextView) findViewById(R.id.eight);
    nine = (TextView) findViewById(R.id.nine);
    
    
    
    currentCondition = (TextView) findViewById(R.id.currentWeather1);
    day1 = (TextView) findViewById(R.id.day1);
    day2 = (TextView) findViewById(R.id.day2);
    day3 = (TextView) findViewById(R.id.day3);
    day4 = (TextView) findViewById(R.id.day4);
    day5 = (TextView) findViewById(R.id.day5);
    day6 = (TextView) findViewById(R.id.day6);
    day7 = (TextView) findViewById(R.id.day7);
    day8 = (TextView) findViewById(R.id.day8);
    day9 = (TextView) findViewById(R.id.day9);
    
    imageView0 = (ImageView) findViewById(R.id.imageView0);
    imageView1 = (ImageView) findViewById(R.id.imageView1);
    imageView2 = (ImageView) findViewById(R.id.imageView2);
    imageView3 = (ImageView) findViewById(R.id.imageView3);
    imageView4 = (ImageView) findViewById(R.id.imageView4);
    imageView5 = (ImageView) findViewById(R.id.imageView5);
    imageView6 = (ImageView) findViewById(R.id.imageView6);
    imageView7 = (ImageView) findViewById(R.id.imageView7);
    imageView8 = (ImageView) findViewById(R.id.imageView8);
    imageView9 = (ImageView) findViewById(R.id.imageView9);

    if(isDataConnectionAvailable(this)) {
    	// Get the location manager
    	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
          System.out.println("Provider " + provider + " has been selected.");
          onLocationChanged(location);
        } else {
          latituteField.setText("Location not available");
          longitudeField.setText("Location not available");
        } 
        
        SharedPreferences sharedFlag = getSharedPreferences(PREFS_NAME, 0);
        flag = sharedFlag.getString("Flag", "C");
        
        try {
    		String result = new JSONWeather().execute("http://api.wunderground.com/api/796da11422ba3dc2/forecast10day/q/"+city+".json").get();
    		String current = new JSONWeather().execute("http://api.wunderground.com/api/796da11422ba3dc2/conditions/q/"+city+".json").get();
    		try {
    			jObj = new JSONObject(result);
    			weatherF = new JSONObject(current).getJSONObject("current_observation").getString("temp_f");
    			weatherC = new JSONObject(current).getJSONObject("current_observation").getString("temp_c");
    			currentLoc = new JSONObject(current).getJSONObject("current_observation").getJSONObject("display_location").getString("full");
    			todayIcon = new JSONObject(current).getJSONObject("current_observation").getString("icon_url");
    			currentCondition2 = new JSONObject(current).getJSONObject("current_observation").getString("weather");
    			currentImage = new GetImage().execute(todayIcon).get();
    			test1 = jObj.getJSONObject("forecast").getJSONObject("simpleforecast").getJSONArray("forecastday");

    			no_of_days = sharedFlag.getString("days", "3");
    			j = no_of_days.charAt(0)-48;    			
    			location1.setText(String.valueOf(currentLoc)); 

    			JSONObject j1 = new JSONObject();
    			for(int i=0;i<10;i++){
    				j1 = test1.getJSONObject(i);
    				high[i] =j1.getString("high");
    				low[i] = j1.getString("low");
    				conditions[i] = j1.getString("conditions");
    				iconURL[i] = j1.getString("icon_url");	
    			}
   			
    			
    			JSONObject day1h = new JSONObject(high[0]);
    			JSONObject day1l = new JSONObject(low[0]);
    			JSONObject day2h = new JSONObject(high[1]);
    			JSONObject day2l = new JSONObject(low[1]);
    			JSONObject day3h = new JSONObject(high[2]);
    			JSONObject day3l = new JSONObject(low[2]);
    			JSONObject day4h = new JSONObject(high[3]);
    			JSONObject day4l = new JSONObject(low[3]);
    			JSONObject day5h = new JSONObject(high[4]);
    			JSONObject day5l = new JSONObject(low[4]);
    			JSONObject day6h = new JSONObject(high[5]);
    			JSONObject day6l = new JSONObject(low[5]);
    			JSONObject day7h = new JSONObject(high[6]);
    			JSONObject day7l = new JSONObject(low[6]);
    			JSONObject day8h = new JSONObject(high[7]);
    			JSONObject day8l = new JSONObject(low[7]);
    			JSONObject day9h = new JSONObject(high[8]);
    			JSONObject day9l = new JSONObject(low[8]);

    			
    			four.setVisibility(View.GONE);
				five.setVisibility(View.GONE);
				six.setVisibility(View.GONE);
				seven.setVisibility(View.GONE);
				eight.setVisibility(View.GONE);
				nine.setVisibility(View.GONE);
				
				day4.setVisibility(View.GONE);
				day5.setVisibility(View.GONE);
				day6.setVisibility(View.GONE);
				day7.setVisibility(View.GONE);
				day8.setVisibility(View.GONE);
				day9.setVisibility(View.GONE);
				
				imageView4.setVisibility(View.GONE);
				imageView5.setVisibility(View.GONE);
				imageView6.setVisibility(View.GONE);
				imageView7.setVisibility(View.GONE);
				imageView8.setVisibility(View.GONE);
				imageView9.setVisibility(View.GONE);
				
    			currentCondition.setText(currentCondition2);
    			
//    			Bundle extras = getIntent().getExtras();
//    			if (extras != null) {
//    				if(extras.getString("Flag")!= null){
//    					flag = extras.getString("Flag");
    					
    			if(flag.equals("F")){
    				day1.setText("High " + day1h.getString("fahrenheit")+"°F"  + " Low " + day1l.getString("fahrenheit")+"° F " + conditions[0]);
    				day2.setText("High " + day2h.getString("fahrenheit")+"°F"  + " Low " + day2l.getString("fahrenheit")+" °F " + conditions[1]);
    				day3.setText("High " + day3h.getString("fahrenheit")+"°F"  + " Low " + day3l.getString("fahrenheit")+" °F " + conditions[2]);
    				if(j>3){
    					four.setText("Day 4");
    					four.setVisibility(View.VISIBLE);
    					day4.setVisibility(View.VISIBLE);
    					imageView4.setVisibility(View.VISIBLE);	
    					l1.invalidate();
    					day4.setText("High " + day4h.getString("fahrenheit")+"°F"  + " Low " + day4l.getString("fahrenheit")+" °F " + conditions[3]);
    				}
    				if(j>4){
    					five.setText("Day 5");
    					five.setVisibility(View.VISIBLE);
    					day5.setVisibility(View.VISIBLE);
    					imageView5.setVisibility(View.VISIBLE);
    					l1.invalidate();
        				day5.setText("High " + day5h.getString("fahrenheit")+"°F"  + " Low " + day5l.getString("fahrenheit")+" °F " + conditions[4]);
    				}
    				if(j>5){
    					six.setText("Day 6");
    					six.setVisibility(View.VISIBLE);
    					day6.setVisibility(View.VISIBLE);
    					imageView6.setVisibility(View.VISIBLE);
    					l1.invalidate();
        				day6.setText("High " + day6h.getString("fahrenheit")+"°F"  + " Low " + day6l.getString("fahrenheit")+" °F " + conditions[5]);	
    				}
    				if(j>6){
    					seven.setText("Day 7");
    					seven.setVisibility(View.VISIBLE);
    					day7.setVisibility(View.VISIBLE);
    					imageView7.setVisibility(View.VISIBLE);
    					l1.invalidate();
        				day7.setText("High " + day7h.getString("fahrenheit")+"°F"  + " Low " + day7l.getString("fahrenheit")+" °F " + conditions[6]);	
    				}
    				if(j>7){
    					eight.setText("Day 8");
    					eight.setVisibility(View.VISIBLE);
    					day8.setVisibility(View.VISIBLE);
    					imageView8.setVisibility(View.VISIBLE);
        				day8.setText("High " + day8h.getString("fahrenheit")+"°F"  + " Low " + day8l.getString("fahrenheit")+" °F " + conditions[7]);
    				}
    				if(j>8){
    					nine.setText("Day 9");
    					nine.setVisibility(View.VISIBLE);
    					day9.setVisibility(View.VISIBLE);
    					imageView9.setVisibility(View.VISIBLE);
        				day9.setText("High " + day9h.getString("fahrenheit")+"°F"  + " Low " + day9l.getString("fahrenheit")+" °F " + conditions[8]);	
    				}
    				weather.setText(weatherF+"°F");
    			}
    			else{
    				day1.setText("High " + day1h.getString("celsius")+"°C " + " Low " + day1l.getString("celsius")+" °C " + conditions[0]);
    				day2.setText("High " + day2h.getString("celsius")+"°C " + " Low " + day2l.getString("celsius")+" °C " + conditions[1]);
    				day3.setText("High " + day3h.getString("celsius")+"°C " + " Low " + day3l.getString("celsius")+" °C " + conditions[2]);
    				if(j>3){
    					
    					four.setText("Day 4");
    					four.setVisibility(View.VISIBLE);
    					day4.setVisibility(View.VISIBLE);
    					imageView4.setVisibility(View.VISIBLE);
    					l1.invalidate();
        				day4.setText("High " + day4h.getString("celsius")+"°C " + " Low " + day4l.getString("celsius")+" °C " + conditions[3]);	
        		
    				}
    				if(j>4){
    					five.setText("Day 5");
    					five.setVisibility(View.VISIBLE);
    					day5.setVisibility(View.VISIBLE);
    					imageView5.setVisibility(View.VISIBLE);
    					l1.invalidate();
        				day5.setText("High " + day5h.getString("celsius")+"°C " + " Low " + day5l.getString("celsius")+" °C " + conditions[4]);	
    				}
    				if(j>5){
    					six.setText("Day 6");
    					six.setVisibility(View.VISIBLE);
    					day6.setVisibility(View.VISIBLE);
    					imageView6.setVisibility(View.VISIBLE);
    					l1.invalidate();
        				day6.setText("High " + day6h.getString("celsius")+"°C " + " Low " + day6l.getString("celsius")+" °C " + conditions[5]);	
    				}
    				if(j>6){
    					seven.setText("Day 7");
    					seven.setVisibility(View.VISIBLE);
    					day7.setVisibility(View.VISIBLE);
    					imageView7.setVisibility(View.VISIBLE);
    					l1.invalidate();
        				day7.setText("High " + day7h.getString("celsius")+"°C " + " Low " + day7l.getString("celsius")+" °C " + conditions[6]);	
    				}
    				if(j>7){
    					eight.setText("Day 8");
    					eight.setVisibility(View.VISIBLE);
    					day8.setVisibility(View.VISIBLE);
    					imageView8.setVisibility(View.VISIBLE);
    					l1.invalidate();
        				day8.setText("High " + day8h.getString("celsius")+"°C " + " Low " + day8l.getString("celsius")+" °C " + conditions[7]);	
    				}
    				if(j>8){
    					nine.setText("Day 9");
    					nine.setVisibility(View.VISIBLE);
    					day9.setVisibility(View.VISIBLE);
    					imageView9.setVisibility(View.VISIBLE);
    					l1.invalidate();
        				day9.setText("High " + day9h.getString("celsius")+"°C " + " Low " + day9l.getString("celsius")+" °C " + conditions[8]);	
    				}
    				weather.setText(weatherC+"°C");
    			}	    				 			
			
    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}		
    		for(int i=0;i<10;i++){
    			imageConditions[i] = new GetImage().execute(iconURL[i]).get();
    		}
    		
    		imageView0.setImageBitmap(currentImage);
    		imageView1.setImageBitmap(imageConditions[0]);		
    		imageView2.setImageBitmap(imageConditions[1]);
    		imageView3.setImageBitmap(imageConditions[2]);
    		
    		if(j>3){

        		imageView4.setImageBitmap(imageConditions[3]);	
    		}
    		if(j>4){

        		imageView5.setImageBitmap(imageConditions[4]);	
    		}
    		if(j>5){

        		imageView6.setImageBitmap(imageConditions[5]);	
    		}
    		if(j>6){

        		imageView7.setImageBitmap(imageConditions[6]);	
    		}
    		if(j>7){

        		imageView8.setImageBitmap(imageConditions[7]);	
    		}
    		if(j>8){

        		imageView9.setImageBitmap(imageConditions[8]);	
    		}

    	} catch (InterruptedException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (ExecutionException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}  
    }
    else{
    	Toast.makeText(getBaseContext(), "Please connect to the internet", Toast.LENGTH_LONG).show();
    } 
  }
  /* Request updates at startup */
  @Override
  protected void onResume() {
    super.onResume();
    if(isDataConnectionAvailable(this)) {
    	locationManager.requestLocationUpdates(provider, 400, 1, this);
    }
  }
  /* Remove the locationlistener updates when Activity is paused */
  @Override
  protected void onPause() {
    super.onPause();
    if(isDataConnectionAvailable(this)) {
    	locationManager.removeUpdates(this);
    }
  }
  @Override
  public void onLocationChanged(Location location) {
	  if(isDataConnectionAvailable(this)) {
	    double lat =  location.getLatitude();
	    double lng =  location.getLongitude();
	
	    Geocoder geoCoder1 = new Geocoder(getBaseContext(), Locale.getDefault());
		try {
	
			List<Address> addresses;
			addresses = geoCoder1.getFromLocation(lat,lng, 1);
			
			SharedPreferences cityCode = getSharedPreferences(PREFS_NAME, 0);
			city = cityCode.getString("ZipCode", addresses.get(0).getPostalCode());
			
	//		location1.setText(String.valueOf(add));  
	//		Bundle extras = getIntent().getExtras();
	//		if (extras != null) {
	//			city = extras.getString("ZipCode");
	//		}
	//		else{
	//
	//			city = addresses.get(0).getPostalCode();
	//		}
	//		Toast.makeText(getBaseContext(), city, Toast.LENGTH_SHORT).show();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
  }
  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onProviderEnabled(String provider) {
//    Toast.makeText(this, "Enabled new provider " + provider,Toast.LENGTH_SHORT).show();
  }
  @Override
  public void onProviderDisabled(String provider) {
//    Toast.makeText(this, "Disabled provider " + provider,Toast.LENGTH_SHORT).show();
  }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.weather, menu);
	return true;
  }
  public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.action_settings:
			Intent aboutIntent = new Intent(WeatherActivity.this, SettingsActivity.class);
			startActivity(aboutIntent);
			return true;
		case android.R.id.home:
			finish();
			return true;
		default: 
			return super.onOptionsItemSelected(item);			
		}
	}
  public static boolean isDataConnectionAvailable(Context context){
      ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo info = connectivityManager.getActiveNetworkInfo();
      if(info == null)
          return false;
      return connectivityManager.getActiveNetworkInfo().isConnected();
  }

} 
