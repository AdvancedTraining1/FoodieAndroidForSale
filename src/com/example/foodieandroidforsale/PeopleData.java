package com.example.foodieandroidforsale;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Window;
import android.view.WindowManager;

import com.example.foodieandroidforsale.R;

public class PeopleData {
	private ArrayList<PeopleItem> dishes;
	private SeatToServer m_seatServer;
	public ArrayList<PeopleItem> getData() {
		m_seatServer = new SeatToServer();
		
		
		int[] colors = new int[] { 0x55808080, 0x55808080 };
		dishes = new ArrayList<PeopleItem >();
		
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
				
				int colorPos = i % colors.length;
				int t,col;
				if(colorPos == 0){
					t=R.drawable.jay;
					col = colors[colorPos];
				}	
			    else{
			    	t=R.drawable.image;
			    	col = colors[colorPos];
			    }
				PeopleItem m = new PeopleItem(String.format("%d", i), String.format(
						"NO.%d", i),"Name: "+temp.getString("name"), t,col,temp.getString("_id"),temp.getString("name"));
				dishes.add(m);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return dishes;
	}
}
