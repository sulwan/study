package com.haojishu.builder;


public abstract class Usa implements Good {

	public Packing packing(){
		return  new Banana();
	}

	public abstract float price();
}
