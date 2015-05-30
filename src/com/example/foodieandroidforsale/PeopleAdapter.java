package com.example.foodieandroidforsale;


import com.example.foodieandroidforsale.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.foodieandroidforsale.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PeopleAdapter extends BaseAdapter {
	//public HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();
	private List<PeopleItem> dishes;
	private Context context;
	//ArrayList<Boolean> checkedItem = new ArrayList<Boolean>();
	ArrayList<String> idList = new ArrayList<String>();
	private PeopleAdapter m_adapter;
	private SeatToServer m_seatServer;
	private ArrayList<PeopleItem> m_PeopleData;
	public PeopleAdapter(Context contex, List<PeopleItem> dishes) {
		this.dishes = dishes;
		this.context = contex;
  
	}
	
	@Override
	public int getCount() {
		//Toast.makeText(context, "number" + dishes.size(), Toast.LENGTH_SHORT).show();
		return dishes == null ? 0 : dishes.size();
	}

	@Override
	public Object getItem(int arg0) {
		return dishes == null ? null : dishes.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public String getMomentId(int arg0) {
		return dishes.get(arg0).getId();
	}

	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {
		View view = arg1;
		Holder holder;
		final int p=position;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.dishlist2,null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}

		PeopleItem dish = this.dishes.get(position);
		holder.title.setText(dish.getName());
		holder.text.setText(dish.getDesc());
		view.setBackgroundColor(dish.getColor());
		
		//---------getPic == id of the pic in drawable-------
		holder.iamge.setImageResource(dish.getPic());
		final String id = dish.getId();
		//holder.checked.setVisibility(View.INVISIBLE);
		holder.checked.setOnClickListener(new OnClickCommitBtnListener1(position)); 
		return view;
	}
	
	public void setAdapter(PeopleAdapter a,SeatToServer b,ArrayList<PeopleItem> m) {
		m_adapter = a;
		m_seatServer = b;
		m_PeopleData = m;
	}
	
	private class OnClickCommitBtnListener1 implements OnClickListener {
		private int pos;
		
		public OnClickCommitBtnListener1(int pos) {
			this.pos = pos;
		}
		@Override
		public void onClick(View v) {
			Toast.makeText(context, "On click delete item" + pos, Toast.LENGTH_SHORT).show();
			//
			Toast.makeText(context, dishes.get(pos).getPeopleID(), Toast.LENGTH_SHORT).show();
			Toast.makeText(context, dishes.get(pos).getPeopleName(), Toast.LENGTH_SHORT).show();
		
			//Toast.makeText(context, "number", Toast.LENGTH_SHORT).show();
			String temp = m_seatServer.Get("/service/seat/deletePeople?restaurantId=5201314&Id="+dishes.get(pos).getPeopleID()+"&Name="+m_PeopleData.get(pos).getPeopleName()
			    			);
			//System.out.println(temp);

			dishes.remove(pos);
			m_adapter.notifyDataSetChanged();
			
			//adapter.notifyAll();
		}
	}
	
	private static class Holder {
		ImageView iamge = null;
	    TextView title = null;
	    TextView text = null;
	    //Button button = null;
	    Button checked = null;

		public Holder(View view) {
			iamge = (ImageView) view.findViewById(R.id.img);
    		title = (TextView) view.findViewById(R.id.name);
    		text = (TextView) view.findViewById(R.id.desc);
    		//button = (Button)view.findViewById(R.id.array_button);
    		checked = (Button)view.findViewById(R.id.remove);
    		
		}
	}
}

