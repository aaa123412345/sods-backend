package org.sods.application;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = {"org.sods.*"})
@ComponentScan(basePackages = {"org.sods.*"})
@MapperScan(basePackages = {"org.sods.security.mapper","org.sods.resource.mapper","org.sods.ftp.mapper"})
@EnableScheduling
@EnableMPP
public class SODSMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(SODSMainApplication.class,args);
    }
}
