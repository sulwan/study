package com.haojishu.builder;

public abstract class China implements Good {
	public Packing packing(){
		return new Appale();
	}

	public abstract float price();
}
