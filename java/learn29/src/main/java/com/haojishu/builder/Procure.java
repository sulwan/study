package com.haojishu.builder;

import com.haojishu.prototype.Shop;

import java.util.ArrayList;
import java.util.List;

public class Procure {
	private List<Good> goods = new ArrayList<Good>();

	public void addGood(Good good){
		goods.add(good);
	}

	public float getCost(){
		float cost = 0.0f;
		for (Good good : goods){
			cost += good.price();
		}
		return cost;
	}


	public void  showGoods(){
		for (Good good: goods){
			System.out.println("商品名称: " + good.name());
			System.out.println("打包形式: " + good.packing().pack());
			System.out.println("商品价格: " + good.price());
		}
	}


}
