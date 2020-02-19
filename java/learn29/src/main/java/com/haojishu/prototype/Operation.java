package com.haojishu.prototype;

import java.util.Hashtable;

/**
 * 数据赋值存储
 */
public class Operation {
	private static Hashtable<Integer, Shop> shopHashtable = new Hashtable<Integer, Shop>();

	public static void giveData(){
		Apple apple = new  Apple();
		apple.setId(1);
		apple.setType("进口香蕉");
		shopHashtable.put(apple.getId(), apple);

		Banane banane = new Banane();
		banane.setId(2);
		banane.setType("国产苹果");
		shopHashtable.put(banane.getId(), banane);
	}

	public static Shop getShop(Integer id){
		Shop shop = shopHashtable.get(id);
		return (Shop) shop.clone();
	}
}
