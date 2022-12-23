package org.eie.fyp.sods.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"org.eie.fyp.sods.security"})
@ComponentScan(basePackages = {"org.eie.fyp.sods.security"})
@MapperScan("org.eie.fyp.sods.security.mapper")
public class SODSSecTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SODSSecTestApplication.class,args);
    }
}
