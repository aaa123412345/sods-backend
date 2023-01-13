package org.sods.application;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"org.sods.*"})
@ComponentScan(basePackages = {"org.sods.*"})
@MapperScan({"org.sods.security.mapper","org.sods.resource.mapper"})
@EnableMPP
public class SODSMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(SODSMainApplication.class,args);
    }
}
