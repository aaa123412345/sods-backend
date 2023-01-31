package org.sods.resource.config;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomFastJsonConfig {
    @Bean
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){

        //1. Create the convertor
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //2. Add a new config of fastjson
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializeFilters((ValueFilter)(object, name, value)->{
            if(name.contains("Id")){
                return  value+ "";
            }
            return value;
        });

        converter.setFastJsonConfig(fastJsonConfig);
        return converter;
    }
}
