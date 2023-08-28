package com.neu.his.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: Result.java
 * @Description: 结果集
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:03:23
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = -6691656851198107463L;
    private int status;
    private String message;
    private Object data;
}