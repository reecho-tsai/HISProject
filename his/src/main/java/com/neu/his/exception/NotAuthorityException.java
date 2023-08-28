package com.neu.his.exception;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: NotAuthorityException.java
 * @Description: 无权限异常
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:00:15
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
public class NotAuthorityException extends MyException {
    private static final long serialVersionUID = 6445638039842655171L;

    public NotAuthorityException(int code, String message) {
        super(code, message);
    }

    public NotAuthorityException() {
        super("没有该权限操作");
    }
}