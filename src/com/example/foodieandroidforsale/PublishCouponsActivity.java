package com.example.foodieandroidforsale;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONStringer;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PublishCouponsActivity extends Activity {
    private EditText editTextInput;
    private EditText editTextContent;
    private Button buttonInputOK;
    private Button buttonInputDone;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_coupons);
        editTextInput = (EditText)findViewById(R.id.editTextInput);
        editTextContent = (EditText)findViewById(R.id.editTextInput_01);
        buttonInputOK = (Button)findViewById(R.id.buttonInputOK);
        buttonInputDone = (Button)findViewById(R.id.buttonInputDone);
		
        buttonInputOK.setOnClickListener(new OnClickListener() {
            
            @Override
			public void onClick(View v) {
            	SharedPreferences preferences = getSharedPreferences("Text", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("title", getTextTitle());
                editor.putString("content", getTextContent());
                editor.commit();       
                Intent temp = new Intent(getApplicationContext(), WriteToTagActivity.class);
                startActivityForResult(temp, 0);
             }
        });
        
        buttonInputDone.setOnClickListener(new OnClickListener() {
            
            @Override
			public void onClick(View v) {
                
                SharedPreferences preferences = getSharedPreferences("Text", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("title", getTextTitle());
                editor.putString("content", getTextContent());
                
                if(editor.commit()) {
                    setResult(Activity.RESULT_OK);
                   
                }
                finish();
             }
        });
        
       
    }
    
    private String getTextContent()
    {
    	String temp = editTextContent.getText().toString();
    	if (temp!=null && !"".equals(temp)) {
			return temp;
		}else
		{
			return "";
		}
    }
    
    private String getTextTitle()
    {
    	String temp = editTextInput.getText().toString();
    	if (temp!=null && !"".equals(temp)) {
			return temp;
		}else
		{
			return "";
		}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
        if(requestCode == 0) {
            if(resultCode == Activity.RESULT_OK) {
            	//��ȡ��ŵ����
                //SharedPreferences preferences = getSharedPreferences("Text", 0);
                //displayContent.setText(preferences.getString("text", null));
                //Toast.makeText(PublishCouponsActivity.this, "haole",Toast.LENGTH_LONG ).show();
                //Toast.makeText(PublishCouponsActivity.this, preferences.getString("content", null),Toast.LENGTH_LONG ).show();
            }
         }
        	
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }

	    
    @Override
    public void onPause() {
        super.onPause();
    }
    

}
