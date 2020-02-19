package com.haojishu;

import com.haojishu.abstractFactory.IProductFctory.BreedFactory;
import com.haojishu.builder.GoodService;
import com.haojishu.factory.NeedsFactory;
import com.haojishu.prototype.Operation;
import com.haojishu.singleton.SingletonTest;

public class main {
  public static void main(String[] args) {
    // 工厂模式
    System.out.println("----------测试工厂模式开始----------");
    System.out.println(new NeedsFactory().getNeeds("phone").make());
    System.out.println(new NeedsFactory().getNeeds("computer").make());

    // 抽象工厂
    System.out.println("----------抽象工厂开始----------");
    System.out.println(new BreedFactory().getBreed("apple").BreedName());
    System.out.println(new BreedFactory().getPlace("sjz").PlaceName());

    // 单例模式
    System.out.println("----------单例模式开始----------");
    System.out.println(SingletonTest.getInstance());
    System.out.println(SingletonTest.getInstance());

    // 原型模式
    System.out.println("----------原型模式----------");
    Operation.giveData();
    System.out.println(Operation.getShop(1).getType());
    System.out.println(Operation.getShop(2).getType());

    System.out.println(Operation.getShop(1));
    System.out.println(Operation.getShop(1));

    // 建造者模式
    System.out.println("----------建造者模式----------");
    new GoodService().Banana().showGoods();
    System.out.println("商品总价格: " + new GoodService().Banana().getCost());
  }
}
