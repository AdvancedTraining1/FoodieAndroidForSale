package com.example.foodieandroidforsale;


public class PeopleItem {

	private String id;
	private String dishName;
	private String desc;
	private int pic;
	private int color;
	
	private String _id;
	private String _name;
	public PeopleItem(String id, String dishName,String desc,int pic,int color,String _id,String _name) {
		super();
		this.id = id;
		this.dishName = dishName;
		this.desc = desc;
		this.pic = pic;
		this.color = color;
		this._id = _id;
		this._name = _name;
	}

	public int getColor() {
		return color;
	}
	
	
	public void setColor(int color) {
		this.color =color;
	}
	
	
	public String getId() {
		return id;
	}
	
	public String getPeopleID() {
		return _id;
	}
	
	public String getPeopleName() {
		return _name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getPic() {
		return pic;
	}

	public void setPic(int pic) {
		this.pic = pic;
	}


	public String getName() {
		return dishName;
	}

	public void setName(String dishName) {
		this.dishName = dishName;
	}


}

