package com.example.foodieandroidforsale;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BulkCoupons extends Activity {
    private EditText editTextTitle;
    private EditText editTextContent;
    private Button buttonInputOK; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulk_coupons);
        editTextTitle = (EditText)findViewById(R.id.editMessageTitle);
        editTextContent = (EditText)findViewById(R.id.editMessageContent);
        buttonInputOK = (Button)findViewById(R.id.sendMsg);
        
        buttonInputOK.setOnClickListener(new OnClickListener() {      
            public void onClick(View v) {
            	
            	if (getTextTitle()==null || "".equals(getTextTitle())
            			|| getTextContent()==null || "".equals(getTextContent())) {
            		Toast.makeText(BulkCoupons.this, "题目和内容不能为空，请重新填写",Toast.LENGTH_LONG ).show(); 
            		return;
        		}
            			
    			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()       
                .detectDiskReads()       
                .detectDiskWrites()       
                .detectNetwork()   // or .detectAll() for all detectable problems       
                .penaltyLog()       
                .build()); 
            			
    			 String serviceAddr = "http://101.200.174.49:3000/service/pushmsg"; 
                 List <NameValuePair> params=new ArrayList<NameValuePair>();  
                 params.add(new BasicNameValuePair("msg",getTextTitle()));  
                 params.add(new BasicNameValuePair("head",getTextContent()));
                 
                 String result = null;
     			 BufferedReader reader = null;
     			try {
     				DefaultHttpClient client = new DefaultHttpClient();
     				HttpPost request = new HttpPost();
     				request.setURI(new URI(serviceAddr));
     				

     				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params,HTTP.UTF_8);
     				request.setEntity(formEntity);

     				System.out.println("url-------"+request.toString());
     				HttpResponse response = client.execute(request);
     				reader = new BufferedReader(new InputStreamReader(response
     						.getEntity().getContent()));

     				StringBuffer strBuffer = new StringBuffer("");
     				String line = null;
     				while ((line = reader.readLine()) != null) {
     					strBuffer.append(line);
     				}
     				result = strBuffer.toString();

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
                System.out.println(result);
     
                
                SharedPreferences preferences = getSharedPreferences("Text", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("title", getTextTitle());
                editor.putString("content", getTextContent());
                
                if(editor.commit()) {
                    setResult(998);
                   
                }
                finish();
               }
 
        });
        
        
    }
    
    private String getTextContent()
    {
    	String temp = editTextContent.getText().toString();
    	return temp;
    }
    
    private String getTextTitle() {
    	String temp = editTextTitle.getText().toString();
    	return temp;
	}
    

}

