package com.neu.his.exception;

import com.neu.his.utils.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: MyException.java
 * @Description: 通用异常处理模块
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:59:54
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MyException extends RuntimeException {
    private static final long serialVersionUID = 6445638039342655172L;
    private Integer code;
    private String message;

    public MyException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public MyException(String message) {
        super(message);
        this.message = message;
        this.code = Constants.DEFAULT_EXCEPTION_CODE;
    }

    public MyException() {
        super(Constants.DEFAULT_EXCEPTION_MSG);
        this.message = Constants.DEFAULT_EXCEPTION_MSG;
        this.code = Constants.DEFAULT_EXCEPTION_CODE;
    }
}