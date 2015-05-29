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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WriteToTagActivity extends Activity{
	private Button buttonInputOK; 
    private JSONStringer jsonText;
    
    // NFC parts
 	private static NfcAdapter mAdapter;
 	private static PendingIntent mPendingIntent;
 	private static IntentFilter[] mFilters;
 	private static String[][] mTechLists;
 	private Tag mytag;
 	
 	 @Override
	protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.scan_tag);
         
         
         buttonInputOK = (Button)findViewById(R.id.buttonNewInput);
         mAdapter = NfcAdapter.getDefaultAdapter(this);

         mPendingIntent = PendingIntent.getActivity(this, 0,
                 new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

         IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
         try {
             ndef.addDataType("*/*");
         } catch (MalformedMimeTypeException e) {
             throw new RuntimeException("fail", e);
         }
         mFilters = new IntentFilter[] {
                 ndef,
         };

         // Setup a tech list for all MifareClassic tags
         mTechLists = new String[][] { new String[] { MifareClassic.class.getName() } };
 		
         buttonInputOK.setOnClickListener(new OnClickListener() {
             
             @Override
			public void onClick(View v) {
            	//��ȡ��ŵ����
                // SharedPreferences preferences = getSharedPreferences("Text", 0);
                 //Toast.makeText(WriteToTagActivity.this, preferences.getString("title", null),Toast.LENGTH_LONG ).show();
                // Toast.makeText(WriteToTagActivity.this, preferences.getString("content", null),Toast.LENGTH_LONG ).show();
                 
            	 setResult(Activity.RESULT_OK);

                 finish();
              }
         });
         
         jsonText = new JSONStringer();  
         // ������{������ʼ��object��endObject�������ʹ��  
         
     }
     
     
     @Override
     public void onResume() {
         super.onResume();
         mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
         WriteModeOn();
     }

     @Override
     public void onNewIntent(Intent intent) {

    	 //������ʾ��
         AlertDialog.Builder m_sure = new AlertDialog.Builder(WriteToTagActivity.this);
         m_sure.setMessage("д��ɹ�!");
         m_sure.setPositiveButton("OK", new DialogInterface.OnClickListener() {
 			
 			@Override
 			public void onClick(DialogInterface dialog, int which) {
 				setResult(Activity.RESULT_OK);
                finish();
 			}
 		});
         
		final AlertDialog dialog = m_sure.create();    
		Window window = dialog.getWindow();    
		WindowManager.LayoutParams lp = window.getAttributes();       
		lp.alpha = 0.8f;  
		lp.y = -180;
		window.setAttributes(lp);
         
		//writeMsg(intent);
		if (writeMsg(intent)) {
			dialog.show();
		}
		
		
// 		Parcelable[] rawArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);  
// 		NdefMessage mNdefMsg = (NdefMessage)rawArray[0];  
// 		NdefRecord mNdefRecord = mNdefMsg.getRecords()[0];  
// 		try {  
// 		    if(mNdefRecord != null){  
// 		       
// 		        //mText.setText("Discover a coupon! " + readResult );
// 		        
// 		        String readResult  = new String(mNdefRecord.getPayload(),"UTF-8");  
//		        Toast.makeText(getApplicationContext(), readResult,Toast.LENGTH_SHORT).show();
// 		        dialog.show();
// 		     }  
// 		}  
// 		catch (UnsupportedEncodingException e) {  
// 		     e.printStackTrace();  
// 		};  
 		
 		

 		
 	
     	
         //write
//         if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) 
//         		|| NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()) 
//         		|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction()) )
//         {
//         	 mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//          	if (mytag != null) {
//          		//Toast.makeText(context, mytag.toString(), Toast.LENGTH_SHORT).show();
//          		Toast.makeText(getApplicationContext(),  mytag.toString(),Toast.LENGTH_SHORT).show();
//          		 try {
//          			//String temp = " Eat food as much as you can!!!";
// 					write(jsonText.toString(), mytag);
// 					//Toast.makeText(getApplicationContext(),  mytag.toString(),Toast.LENGTH_SHORT).show();
// 				} catch (IOException e) {
// 					// TODO Auto-generated catch block
// 					e.printStackTrace();
// 				} catch (FormatException e) {
// 					// TODO Auto-generated catch block
// 					e.printStackTrace();
// 				}
//          	}
//         }

     }
     
     private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
 		//String lang = "";
 		byte[] textBytes = text.getBytes();

 		NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], textBytes);
 		return recordNFC;
 	}
     
     private void write(String text, Tag tag) throws IOException, FormatException {
 		NdefRecord[] records = { createRecord(text) };
 		NdefMessage message = new NdefMessage(records);
 		// Get an instance of Ndef for the tag.
 		Ndef ndef = Ndef.get(tag);
 		if (ndef != null) {
 			// Enable I/O
 			ndef.connect();
 			// Write the message
 			ndef.writeNdefMessage(message);
 			// Close the connection
 			ndef.close();
 		} else {
 			NdefFormatable format = NdefFormatable.get(tag);
 			if (format != null) {
 				try{
 						format.connect();
 						format.format(message);
 						//Log.e(this.tag,"Formatted tag and wrote message");
 				} catch (IOException e) {
 					//Log.e(this.tag,"Failed to format tag.");
 				}
 			} else {
 				//Log.e(this.tag,"Tag doesn't support NDEF.");
 			}
 		}
 	}
     
     
     private Boolean writeMsg(Intent intent)
     {
    	 SharedPreferences preferences = getSharedPreferences("Text", 0);
         //Toast.makeText(WriteToTagActivity.this, preferences.getString("title", null),Toast.LENGTH_LONG ).show();
         //Toast.makeText(WriteToTagActivity.this, preferences.getString("content", null),Toast.LENGTH_LONG ).show();
    	if (preferences.getString("title", null)==null || "".equals(preferences.getString("title", null))
    			|| preferences.getString("content", null)==null || "".equals(preferences.getString("content", null))) {
    		Toast.makeText(WriteToTagActivity.this, "��Ŀ�����ݲ���Ϊ�գ���������д",Toast.LENGTH_LONG ).show(); 
    		return false;
		}
    	 
    	 
         try {
 			jsonText.object();
 			jsonText.key("id");  
 			
 	        jsonText.value("1001");  
 	        jsonText.key("title");  
 	        jsonText.value(preferences.getString("title", null));
 	        jsonText.key("text");  
 	        jsonText.value(preferences.getString("content", null));  
 	          
 	        // }���������  
 	        jsonText.endObject();  
 		} catch (JSONException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} 
    	 
       if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) 
 		|| NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()) 
 		|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction()) )
       {
     	  	mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
 		 	if (mytag != null) {
 		 		//Toast.makeText(context, mytag.toString(), Toast.LENGTH_SHORT).show();
 		 		//Toast.makeText(getApplicationContext(),  mytag.toString(),Toast.LENGTH_SHORT).show();
 		 		 try {
 		 			//String temp = " Eat food as much as you can!!!";
 					write(jsonText.toString(), mytag);
 					return true;
 					//Toast.makeText(getApplicationContext(),  mytag.toString(),Toast.LENGTH_SHORT).show();
 				} catch (IOException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				} catch (FormatException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
 		 	}
       }
     	return false;
     }
    
 	    
     @Override
     public void onPause() {
         super.onPause();
         mAdapter.disableForegroundDispatch(this);
     }
     
     private void WriteModeOn() { 
     	//mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, null);
 	}

 	private void WriteModeOff() {
 		mAdapter.disableForegroundDispatch(this);
 	}
}
