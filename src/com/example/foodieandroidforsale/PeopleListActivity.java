package com.example.foodieandroidforsale;


import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import android.widget.AdapterView;   
import android.widget.AdapterView.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.foodieandroidforsale.R;


public class PeopleListActivity extends Activity implements OnClickListener,
CreateNdefMessageCallback,OnNdefPushCompleteCallback{
    
	
	private ListView m_listview;
    private ArrayList<PeopleItem> m_DishData;
    private PeopleAdapter m_adapter2;
    
    private SeatToServer m_seatServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peoplelist);
        //mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        
        
       
        
        m_listview = (ListView)findViewById(R.id.listview0);
        m_DishData = (new PeopleData()).getData();
        //dishData=getDish();
        //m_adapter = new FullDishItemsAdapter(this,m_DishData);	
        //m_listview.setAdapter(m_adapter);
        
        m_adapter2 = new PeopleAdapter(this, m_DishData);
        m_listview.setAdapter(m_adapter2);
        m_seatServer = new SeatToServer();
        m_adapter2.setAdapter(m_adapter2,m_seatServer,m_DishData);
//        m_listview.setOnItemClickListener(new OnItemClickListener(){   
//            @Override   
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,   
//                    long arg3) {   
//            	m_DishData.get(arg2);
//            	//Toast.makeText(getApplicationContext(), m_DishData.get(arg2).getPeopleID(), Toast.LENGTH_SHORT).show();
//            	
//            }   
//               
//        });
        
        //m_submit.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) {	

    	
    	}

	@Override
	public void onNdefPushComplete(NfcEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

}

