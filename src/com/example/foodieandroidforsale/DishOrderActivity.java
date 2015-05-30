package com.example.foodieandroidforsale;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class DishOrderActivity extends Activity implements CreateNdefMessageCallback{

	TextView mTextView = null;
	NfcAdapter mNfcAdapter;
	private ListView m_listview;
	private FullDishItemsAdapter2 m_adapter2;
    private List<DishItem> m_DishData = new ArrayList<DishItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish_order);
		
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		
		mTextView = (TextView) this.findViewById(R.id.dishlist);
        m_listview = (ListView)findViewById(R.id.listView1);
        
     
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
          
     //     mTextView.setText(new String(msg.getRecords()[0].getPayload()));
          String messageString = new String(msg.getRecords()[0].getPayload());
          
          System.out.println("transt message----"+messageString);
//          DishOrderActivity.this.finish();
//          Intent intent1=new Intent();
//          intent1.putExtra("message", messageString);
//          intent1.setClass(DishOrderActivity.this, OrderListActivity.class);
//          startActivity(intent1);
          
          String[] sourceStrArray = messageString.split(";");
          System.out.println("array length------"+sourceStrArray.length);
          
          for (int i = 0; i < sourceStrArray.length; i=i+5) {
			String id = sourceStrArray[i];
			String dishName = sourceStrArray[i+1];
	        String desc = sourceStrArray[i+3];
	        int pic=Integer.parseInt(sourceStrArray[i+2]);
	        int col=Integer.parseInt(sourceStrArray[i+4]);
	        DishItem dishitem = new DishItem(id, dishName, desc,pic,col);
	        System.out.println("dish is ---" + dishitem);
	        m_DishData.add(dishitem);
			
		}
          
          System.out.println("dish data size----" + m_DishData.size());
      	
          m_adapter2 = new FullDishItemsAdapter2(this, m_DishData);
          m_listview.setAdapter(m_adapter2);
          mTextView.setText("Order is:");
          
			
          
      }
}