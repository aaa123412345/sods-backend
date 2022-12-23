package org.sods.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"org.eie.fyp.sods.*"})
@ComponentScan(basePackages = {"org.eie.fyp.sods.*"})


//@MapperScan("org.eie.fyp.sods.security.mapper")
@MapperScan("org.eie.fyp.sods.security.mapper")
public class SODSMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(SODSMainApplication.class,args);
    }
}
