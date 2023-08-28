package com.neu.his.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: PassToken.java
 * @Description: 用来跳过验证的PassToken
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {
    boolean required() default true;
}