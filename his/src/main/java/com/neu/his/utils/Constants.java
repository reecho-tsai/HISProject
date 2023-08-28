package com.neu.his.utils;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 * <p>
 * &#064;ClassName:  Constants.java
 * &#064;Description:  状态码
 * <p>
 * &#064;version:  v1.0.0
 * &#064;author:  msprc
 * &#064;date:  2022年12月6日 下午3:02:23
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
public class Constants {
    public static final int RESULT_CODE_SUCCESS = 200;
    public static final String SUCCESS_MSG = "SUCCESS";

    public static final int RESULT_CODE_FAIL = 400;
    public static final String FAIL_MSG = "FAIL";

    public static final int DEFAULT_EXCEPTION_CODE = 500;
    public static final String DEFAULT_EXCEPTION_MSG = "服务器发生错误，正在联系管理员小哥哥进行修复";

    public static final String NOT_LOGIN_MSG = "用户未登录或登录信息已过期，请重新登录";

    /**
     * 已挂号
     */
    public static final Integer REGISTER_REGIST = 1;
    /**
     * 已接诊
     */
    public static final Integer REGISTER_DEAL = 2;
    /**
     * 已退号
     */
    public static final Integer REGISTER_RETURN = 3;


    //申请检查的状态
    /**
     * 待缴费
     */
    public static final Integer CHECK_APPLY_STATUS_1 = 1;
    /**
     * 待检查
     */
    public static final Integer CHECK_APPLY_STATUS_2 = 2;
    /**
     * 已检查
     */
    public static final Integer CHECK_APPLY_STATUS_3 = 3;
    /**
     * 已退费
     */
    public static final Integer CHECK_APPLY_STATUS_4 = 4;


    //申请检验的状态
    /**
     * 待缴费
     */
    public static final Integer INSPECT_APPLY_STATUS_1 = 1;
    /**
     * 待检查
     */
    public static final Integer INSPECT_APPLY_STATUS_2 = 2;
    /**
     * 已检查
     */
    public static final Integer INSPECT_APPLY_STATUS_3 = 3;
    /**
     * 已退费
     */
    public static final Integer INSPECT_APPLY_STATUS_4 = 4;
}