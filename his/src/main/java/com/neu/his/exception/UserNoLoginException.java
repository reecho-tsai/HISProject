package com.neu.his.exception;

import com.neu.his.utils.Constants;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: UserNoLoginException.java
 * @Description: 用户未登录异常
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:00:42
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
public class UserNoLoginException extends MyException {
    private static final long serialVersionUID = 6445638039342655171L;

    public UserNoLoginException(int code, String message) {
        super(code, message);
    }

    public UserNoLoginException() {
        super(Constants.NOT_LOGIN_MSG);
    }
}