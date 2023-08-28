package com.neu.his.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: CrossConfig.java
 * @Description: 设置过滤器规则
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@Configuration
public class CrossConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //允许跨域请求的域名
                .allowedOrigins("*")
                //允许的方法
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                //是否允许证书
                .allowCredentials(true)
                //允许时间
                .maxAge(3600)
                //请求头
                .allowedHeaders("*");
    }
}
