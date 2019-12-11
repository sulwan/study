package com.haojishu.controller;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class siteController {

  @Autowired private CuratorFramework client;

  private static final String path = "/sulwan";

  @RequestMapping(value = "/")
  public String hello() throws Exception {

    // 判断节点是否存在
    if (client.checkExists().forPath(path) == null) {
      // 创建根节点(默认是持久节点)
      client.create().forPath(path);
    }

    // 读取节点数据
    byte[] bytes = client.getData().forPath(path);
    // 打印读出来的数据
    System.out.println(new String(bytes));

    // 给节点内写入数据
    client.setData().forPath(path, "我是更新数据".getBytes());
    // 读取节点数据
    System.out.println(new String(client.getData().forPath(path)));

    // 删除一个节点
    client.delete().forPath(path);
    if (client.checkExists().forPath(path) == null) {
      System.out.println("节点不存在");
    }

    return "操作完毕";
  }
}
