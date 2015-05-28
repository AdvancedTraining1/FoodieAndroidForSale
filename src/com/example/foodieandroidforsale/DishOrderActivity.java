package com.example.foodieandroidforsale;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DishOrderActivity extends Activity implements CreateNdefMessageCallback{

	TextView mTextView = null;
	NfcAdapter mNfcAdapter;
	private  ArrayList<String> dishlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		
		mTextView = (TextView) this.findViewById(R.id.dishlist);
		
		mNfcAdapter.setNdefPushMessageCallback(this, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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


	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	   @Override
       public void onResume() {
           super.onResume();
           // Check to see that the Activity started due to an Android Beam
        
           if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
               processIntent(getIntent());
           }
       }

       @Override
       public void onNewIntent(Intent intent) {
           // onResume gets called after this to handle the intent
    	   AlertDialog.Builder m_sure = new AlertDialog.Builder(DishOrderActivity.this);
           m_sure.setMessage("get a dish order");
           m_sure.setPositiveButton("OK", new DialogInterface.OnClickListener() {
   			
   			@Override
   			public void onClick(DialogInterface dialog, int which) {
   				// TODO Auto-generated method stub
   				//Toast.makeText(getApplicationContext(), "To another page",Toast.LENGTH_SHORT).show();
   				mTextView.setText("get a dish order!" );
   				
   			
   			}
   		});
        //   setIntent(intent);
       }
       
       void processIntent(Intent intent) {
      	 System.out.println("processIntent.......");
          Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                  NfcAdapter.EXTRA_NDEF_MESSAGES);
          // only one message sent during the beam
          NdefMessage msg = (NdefMessage) rawMsgs[0];
          System.out.println("transfor information:"+new String(msg.getRecords()[0].getPayload()));
          // record 0 contains the MIME type, record 1 is the AAR, if present
          
          String listString = new String(msg.getRecords()[0].getPayload());
          String[] lists=listString.split(",");
          for (int i = 1; i < lists.length; i++) {
        	  dishlist.add(lists[i]);
		}
          
          mTextView.setText(new String(msg.getRecords()[0].getPayload()));
      }
}
