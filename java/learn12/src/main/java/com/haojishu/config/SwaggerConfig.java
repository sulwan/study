package com.haojishu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  /** 创建API */
  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
        .apiInfo(apiInfo())
        // 设置哪些接口暴露给Swagger展示
        .select()
        // 扫描所有有注解的api，用这种方式更灵活
        //        .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
        // 扫描指定包中的swagger注解
        // .apis(RequestHandlerSelectors.basePackage("com.haojishu.controller"))
        // 扫描所有 .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }

  /** 添加摘要信息 */
  private ApiInfo apiInfo() {
    // 用ApiInfoBuilder进行定制
    return new ApiInfoBuilder()
        // 设置标题
        .title("标题：API接口文档演示")
        // 描述
        .description("描述：这就是方便查看API的文档")
        // 作者信息
        .contact(new Contact("心肝宝贝", null, null))
        // 版本
        .version("版本号:" + "1.0")
        .build();
  }
}
