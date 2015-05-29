package com.example.foodieandroidforsale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddSeatNumActivity extends Activity {
	TextView titleTextView;
	EditText seatNum;
	Button setButton;
	String seatNumString;
	String message;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_seat_num);
		
		titleTextView = (TextView) findViewById(R.id.textView1);
		seatNum = (EditText) findViewById(R.id.edit_seat_num);
		setButton = (Button) findViewById(R.id.btn_set_seat);
		
		setButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				seatNumString = seatNum.getText().toString();
				
				if(TextUtils.isEmpty(seatNumString.trim())){
					Toast.makeText(v.getContext(), "Seat num can't be empty", Toast.LENGTH_LONG).show();
				}
				
				StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()       
                .detectDiskReads()       
                .detectDiskWrites()       
                .detectNetwork()   // or .detectAll() for all detectable problems       
                .penaltyLog()       
                .build()); 
            			
    			 String serviceAddr = "http://101.200.174.49:3000/service/seat/setseatnum"; 
                 List <NameValuePair> params=new ArrayList<NameValuePair>();  
                 params.add(new BasicNameValuePair("restaurantId","110"));  
                 params.add(new BasicNameValuePair("seatsNum",seatNumString));
                 
                 String result = null;
     			 BufferedReader reader = null;
     			try {
     				DefaultHttpClient client = new DefaultHttpClient();
     				HttpPost request = new HttpPost();
     				request.setURI(new URI(serviceAddr));
     				

     				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params,HTTP.UTF_8);
     				request.setEntity(formEntity);

     				HttpResponse response = client.execute(request);
     				reader = new BufferedReader(new InputStreamReader(response
     						.getEntity().getContent()));

     				StringBuffer strBuffer = new StringBuffer("");
     				String line = null;
     				while ((line = reader.readLine()) != null) {
     					strBuffer.append(line);
     				}
     				result = strBuffer.toString();
     				
     				JSONObject jsonObject = new JSONObject(result);
     				message = jsonObject.getString("message");

     			} catch (Exception e) {
     				e.printStackTrace();
     			} finally {
     				if (reader != null) {
     					try {
     						reader.close();
     						reader = null;
     					} catch (IOException e) {
     						e.printStackTrace();
     					}
     				}
     			}
                
                new Thread()
				{
					@Override
					public void run(){
						try{
							
							handler.post(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(AddSeatNumActivity.this,message, Toast.LENGTH_SHORT).show();
								}
							});
						}catch(Exception e){
							
						}finally{
							AddSeatNumActivity.this.finish();
						
						}
					}
				}.start();   
                
          
                finish();
               }
				
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_seat_num, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
