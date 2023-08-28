package com.neu.his.exception;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: NotExistException.java
 * @Description: 信息不存在（空指针）异常
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:00:29
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
public class NotExistException extends MyException {
    private static final long serialVersionUID = 6445638039852655171L;

    public NotExistException(int code, String message) {
        super(code, message);
    }

    public NotExistException() {
        super("查询信息不存在");
    }
}
