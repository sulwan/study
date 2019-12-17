package com.haojishu.config.datasource;

import com.haojishu.enums.DataSourceType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/** 这是一个配置文件 */
@Configuration
public class MybatisConfig {

  /**
   * 设置主数据库
   *
   * @return
   */
  @Bean
  @ConfigurationProperties("spring.datasource.druid.master")
  public DataSource masterDataSource() {
    return DataSourceBuilder.create().build();
  }

  /**
   * 设置从数据库
   *
   * @return
   */
  @Bean
  @ConfigurationProperties("spring.datasource.druid.slave")
  @ConditionalOnProperty(
      prefix = "spring.datasource.druid.slave",
      name = "enabled",
      havingValue = "true")
  public DataSource slaveDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "dynamicDataSource")
  @Primary
  public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource) {
    Map<Object, Object> targetDataSources = new HashMap<>();
    targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
    targetDataSources.put(DataSourceType.SLAVE.name(), slaveDataSource);
    return new DynamicDataSource(masterDataSource, targetDataSources);
  }
}
