package com.haojishu.builder;

public class GoodService {
  public Procure Banana() {
    Procure procure = new Procure();
    procure.addGood(new ChinaReal());
    procure.addGood(new UsaReal());
    return procure;
  }
}
