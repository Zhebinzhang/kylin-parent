package com.kylin.upms.biz;

import com.kylin.aop.aop.LogAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@SpringBootApplication(exclude={RedisAutoConfiguration.class})
@MapperScan(basePackages={"com.kylin.upms.biz.mapper"})
@EnableSwagger2
@EnableDiscoveryClient
@RefreshScope
public class SpringBootMain {
    public static void main(String[] args) {
        System.out.println("xxxxxxxxxxxxxxx"+urlRelease);
        SpringApplication.run(SpringBootMain.class,args);
    }

    @Value(value = "${urls}")
    private static  List<String> urlRelease;

    @Bean
    public LogAspect logAspect()
    {
        return new LogAspect();
    }
}
