/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.foodieandroidforsale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Generic UI for sample discovery.
 */
public class CardReaderFragment extends Fragment implements LoyaltyCardReader.AccountCallback {

    public static final String TAG = "CardReaderFragment";
    // Recommend NfcAdapter flags for reading from other Android devices. Indicates that this
    // activity is interested in NFC-A devices (including other Android devices), and that the
    // system should not check for the presence of NDEF-formatted data (e.g. Android Beam).
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
    public LoyaltyCardReader mLoyaltyCardReader;
    private TextView mAccountField;
    private TextView mSeatNum;
    private TextView mPeopleNum;
    private Context m_Context;
    private int m_Seat;
    private SeatToServer m_seatServer;
    /** Called when sample is created. Displays generic UI with welcome text. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        if (v != null) {
        	m_seatServer = new SeatToServer();
        	
            mAccountField = (TextView) v.findViewById(R.id.card_account_field);
            mSeatNum = (TextView)v.findViewById(R.id.card_account_label);
            mPeopleNum = (TextView)v.findViewById(R.id.people_account_label);
            mAccountField.setText("Waiting...");
            m_Seat = getSeatNumber();
            mSeatNum.setText(String.format("%d", m_Seat));
            mPeopleNum.setText(getPeopleNumber());
            mLoyaltyCardReader = new LoyaltyCardReader(this);
            
            
            // Disable Android Beam and register our card reader callback
            enableReaderMode();
            
            
        }

        return v;
    }
    
    public void setContext(Context c) {
		m_Context = c;
	}
    
    public int getSeatNumber()
    {
    	String value = null;
    	String temp = m_seatServer.Get("/service/seat/getseatnum?restaurantId=5201314");
    	System.out.println(temp);
    	try {
			JSONObject myJsonObject = new JSONObject(temp);
			 value = myJsonObject.getString("num");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return Integer.parseInt(value);
    }
    
    public String getPeopleNumber()
    {
    	String value = null;
    	String temp = m_seatServer.Get("/service/seat/getPeopleNum?restaurantId=5201314");
    	System.out.println(temp);
    	try {
			JSONObject myJsonObject = new JSONObject(temp);
			 value = myJsonObject.getString("num");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return value;
    }

    @Override
    public void onPause() {
        super.onPause();
        disableReaderMode();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableReaderMode();
        mPeopleNum.setText(getPeopleNumber());
    }

    private void enableReaderMode() {
        //Log.i(TAG, "Enabling reader mode");
        Activity activity = getActivity();
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {
            nfc.enableReaderMode(activity, mLoyaltyCardReader, READER_FLAGS, null);
        }
    }

    private void disableReaderMode() {
        //Log.i(TAG, "Disabling reader mode");
        Activity activity = getActivity();
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {
            nfc.disableReaderMode(activity);
        }
    }

    @Override
    public void onAccountReceived(final String account) {
        // This callback is run on a background thread, but updates to UI elements must be performed
        // on the UI thread.
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {        	
            	
                mAccountField.setText(account);

                //account = "5201314";
                String peopledata = null;
            	JSONArray list = null;
    			peopledata = m_seatServer.Get("/service/seat/getList?restaurantId=5201314");
    			//System.out.println(peopledata);
    			try {
    				JSONObject myJsonObject = new JSONObject(peopledata);
    				list=myJsonObject.getJSONArray("peopleList"); //得到likes数组
    				 //打印Apple Pie
    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			for(int i=0;i<list.length();++i)
    			{
    				try {
    					System.out.println(list.getString(i));
    					JSONObject temp = new JSONObject(list.getString(i));
    					String value = temp.getString("_id");
    					System.out.println(value);
    					if(account.equals(value))
    					{
    						AlertDialog.Builder m_sure = new AlertDialog.Builder(m_Context);
    						m_sure.setMessage("Sign Again!");
    						m_sure.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    		        			public void onClick(DialogInterface dialog, int which) {
    		        		    	
    		        			}
    		        		});
    						final AlertDialog dialog = m_sure.create();    
    		                Window window = dialog.getWindow();    
    		                WindowManager.LayoutParams lp = window.getAttributes();       
    		                lp.alpha = 0.8f;  
    		                lp.y = -180;
    		                window.setAttributes(lp);
    						dialog.show();
    						return;
    					}
    				} catch (JSONException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    			AlertDialog.Builder m_sure = new AlertDialog.Builder(m_Context);
    			String value = null;
		    	String temp = m_seatServer.Get("/service/seat/insertPeople?restaurantId=5201314&Id="+account+"&Name=cmm");
		    	System.out.println(temp);
    			m_sure.setMessage("Sign OK!");
    			m_sure.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        			public void onClick(DialogInterface dialog, int which) {
        		    	
        			}
        		});
    			
    			final AlertDialog dialog = m_sure.create();    
                Window window = dialog.getWindow();    
                WindowManager.LayoutParams lp = window.getAttributes();       
                lp.alpha = 0.8f;  
                lp.y = -180;
                window.setAttributes(lp);
				dialog.show();
                mPeopleNum.setText(getPeopleNumber());
            }
        });
    }
}
