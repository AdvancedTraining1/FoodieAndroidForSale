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

public class PublishCouponsActivity extends Activity {
    private EditText editTextInput;
    private EditText editTextContent;
    private Button buttonInputOK; 
    private JSONStringer jsonText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_coupons);
        editTextInput = (EditText)findViewById(R.id.editTextInput);
        editTextContent = (EditText)findViewById(R.id.editTextInput_01);
        buttonInputOK = (Button)findViewById(R.id.buttonInputOK);
        buttonInputOK.setOnClickListener(new OnClickListener() {
            
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
        
        jsonText = new JSONStringer();  
        // 首先是{，对象开始。object和endObject必须配对使用  
        try {
			jsonText.object();
			jsonText.key("id");  
			
	        jsonText.value("2");  
	        jsonText.key("title");  
	        jsonText.value("Discount");
	        jsonText.key("text");  
	        jsonText.value("80% Discount!");  
	          
	        // }，对象结束  
	        jsonText.endObject();  
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
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
}
