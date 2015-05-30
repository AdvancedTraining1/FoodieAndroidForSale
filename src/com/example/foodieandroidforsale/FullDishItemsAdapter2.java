package com.example.foodieandroidforsale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.data.r;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FullDishItemsAdapter2 extends BaseAdapter {
	public HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();
	private List<DishItem> dishes;
	private Context context;
	ArrayList<Boolean> checkedItem = new ArrayList<Boolean>();
	ArrayList<String> idList = new ArrayList<String>();
	
	
	public FullDishItemsAdapter2(Context contex, List<DishItem> dishes) {
		this.dishes = dishes;
		this.context = contex;
		for(int i=0;i<dishes.size();i++){
            checkedItem.add(i,false);
            idList.add(i,null);
        }
	}
	
	public ArrayList<Boolean> getChecklist() {
		return checkedItem;
	}
	
	public ArrayList<String> getIDList() {
		return idList;
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
			view = LayoutInflater.from(context).inflate(R.layout.dishlist3,null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}

		DishItem dish = this.dishes.get(position);
		holder.title.setText(dish.getName());
		holder.text.setText(dish.getDesc());
		
		int[] colors = new int[] { 0x00010101, 0x55808080 };
		
		int colorPos = position % colors.length;
		if (colorPos==0) {
			view.setBackgroundColor(colors[0]);
			
		}else {
			view.setBackgroundColor(colors[1]);
		}
		if (dish.getPic()==2130837562){
			holder.iamge.setImageResource(R.drawable.jay);
		}else {
			holder.iamge.setImageResource(R.drawable.image);
		}
		
		//---------getPic == id of the pic in drawable-------
	
		final String id = dish.getId();
	
		return view;
	}
	

	private static class Holder {
		ImageView iamge = null;
	    TextView title = null;
	    TextView text = null;
	    //Button button = null;

		public Holder(View view) {
			iamge = (ImageView) view.findViewById(R.id.img);
    		title = (TextView) view.findViewById(R.id.name);
    		text = (TextView) view.findViewById(R.id.desc);
    		//button = (Button)view.findViewById(R.id.array_button);
		}
	}
}

