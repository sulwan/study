package com.haojishu;

import com.haojishu.factory.NeedsFactory;

public class main {
  public static void main(String[] args) {
    // 测试工厂模式
    System.out.println("测试工厂模式开始");
    System.out.println(new NeedsFactory().getNeeds("phone").make());
    System.out.println(new NeedsFactory().getNeeds("computer").make());
    System.out.println("测试工厂模式结束");
  }
}
