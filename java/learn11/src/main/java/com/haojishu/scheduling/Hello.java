package com.haojishu.scheduling;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class Hello {

  @Scheduled(fixedRate = 1000)
  public void site() {
    System.out.println("sulwan");
  }
}
