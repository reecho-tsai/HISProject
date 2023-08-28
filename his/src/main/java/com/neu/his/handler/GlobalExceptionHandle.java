package com.neu.his.handler;

import com.neu.his.exception.MyException;
import com.neu.his.utils.Result;
import com.neu.his.utils.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: GlobalExceptionHandle.java
 * @Description: 捕获并处理全局异常
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:01:26
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@RestControllerAdvice
public class GlobalExceptionHandle {
    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandle.class);

    /**
     * 处理所有不可知的异常 这个注解是捕获所有异常
     */
    @ExceptionHandler(value = MyException.class)
    public Result handleMyException(MyException e, HttpServletRequest request) {
        log.error("msg:{}, url:{}", e.getMessage(), request.getRequestURL());
        e.printStackTrace();
        return ResultGenerator.getFailResult("", e.getMessage(), e.getCode());
    }
}