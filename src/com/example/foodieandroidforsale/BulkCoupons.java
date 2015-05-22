package com.example.foodieandroidforsale;


import org.json.JSONException;
import org.json.JSONStringer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
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

