package com.haojishu.prototype;

public abstract class Shop  implements Cloneable{
	protected  Integer id;
	private String type;
	abstract public String shopName();


	public Object clone(){
		Object clone = null;
		try{
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}

	public Integer getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
