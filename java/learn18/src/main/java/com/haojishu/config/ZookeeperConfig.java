package com.haojishu.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperConfig {

    @Value("${zk.url}")
    private String url;

    @Bean
    public CuratorFramework curatorFramework(){
        ExponentialBackoffRetry exponentialBackoffRetry = new ExponentialBackoffRetry(100, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(url, exponentialBackoffRetry);
        curatorFramework.start();
        return curatorFramework;
    }
}
