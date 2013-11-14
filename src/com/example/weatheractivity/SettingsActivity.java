package com.example.weatheractivity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.showlocationactivity.R;


public class SettingsActivity extends Activity {
	
	public static final String PREFS_NAME = "MyPrefsFile";

	
	private Button submit, celcius, fahrenheit;
	private EditText zipcodetext;
	private String zip, flag, days;
	private Spinner spinner;
//	private int flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setupUI(findViewById(R.id.parent));
		zipcodetext = (EditText) findViewById(R.id.editText1);
		submit = (Button) findViewById(R.id.submit);
		fahrenheit = (Button) findViewById(R.id.fahrenheit);
		celcius = (Button) findViewById(R.id.celcius);
		addItemsOnSpinner();
		spinner = (Spinner) findViewById(R.id.no_of_days);
		fahrenheit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flag = "F";
				fahrenheit.setEnabled(false);
				celcius.setEnabled(true);
				
			}
		});
		celcius.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flag = "C";
				celcius.setEnabled(false);
				fahrenheit.setEnabled(true);
			}
		});
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				zip = zipcodetext.getText().toString();
				if( zip.equals(""))
				 {    
				   zipcodetext.setError( "Zip code cannot be blank" );
				   //You can Toast a message here that the Username is Empty
				 }
				if(zip.length() != 5){
					zipcodetext.setError("Invalid zip code");
				}
				else{
					days = String.valueOf(spinner.getSelectedItem());
					Intent i = new Intent(SettingsActivity.this,WeatherActivity.class);
					SharedPreferences sVar = getSharedPreferences(PREFS_NAME, 0);
					Editor edit = sVar.edit();
					edit.putString("ZipCode", zip);
					edit.putString("Flag", flag);
					edit.putString("days", days);
					edit.commit();
			
					
					
//					Bundle extras = new Bundle();
//					extras.putString("ZipCode", zip);
//					extras.putString("Flag", flag);
//					i.putExtras(extras);
					startActivity(i);
					
				}
				
				
				//Toast.makeText(getBaseContext(), zip, Toast.LENGTH_SHORT).show();
			}
		});
		
		
		
		
	}
	
	public static void hideSoftKeyboard(View v) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  v.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}
	public void setupUI(View view) {

	    //Set up touch listener for non-text box views to hide keyboard.
	    if(!(view instanceof EditText)) {

	        view.setOnTouchListener(new OnTouchListener() {

	            public boolean onTouch(View v, MotionEvent event) {
	                hideSoftKeyboard(v);
	                return false;
	            }

	        });
	    }

	    //If a layout container, iterate over children and seed recursion.
	    if (view instanceof ViewGroup) {

	        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

	            View innerView = ((ViewGroup) view).getChildAt(i);

	            setupUI(innerView);
	        }
	    }
	}
	// add items into spinner dynamically
	  public void addItemsOnSpinner() {
	 
		spinner = (Spinner) findViewById(R.id.no_of_days);
		List<String> list = new ArrayList<String>();
		list.add("3 Days");
		list.add("4 Days");
		list.add("5 Days");
		list.add("6 Days");
		list.add("7 Days");
		list.add("8 Days");
		list.add("9 Days");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	  }
//	int changeUnits(int status){
//		if(status == 0){
//			return 0;
//		}
//		else{
//			return 1;
//		}
//	}

	  public boolean onOptionsItemSelected(MenuItem item){
			switch(item.getItemId()){
			case android.R.id.home:
				finish();
				return true;
			default: 
				return super.onOptionsItemSelected(item);
				
			}
		}

}
