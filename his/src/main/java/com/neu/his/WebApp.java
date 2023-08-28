package com.neu.his;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: WebApp.java
 * @Description: Spring启动器
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:36:58
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@MapperScan("com.neu.his.mapper")
@SpringBootApplication
public class WebApp {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WebApp.class);
        //关闭Banner字符画
        //application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
