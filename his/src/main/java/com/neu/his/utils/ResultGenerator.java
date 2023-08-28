package com.neu.his.utils;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: ResultGenerator.java
 * @Description: 结果集默认值
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:03:41
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
public class ResultGenerator {

    /**
     * @return 默认成功信息 （data=null, msg = SUCCESS , status = 200)
     */
    public static Result getSuccessResult() {
        return getSuccessResult("", Constants.SUCCESS_MSG, Constants.RESULT_CODE_SUCCESS);
    }

    /**
     * @return 带数据的成功信息 （data=data, msg = SUCCESS , status = 200)
     */
    public static Result getSuccessResult(Object data) {
        return getSuccessResult(data, Constants.SUCCESS_MSG, Constants.RESULT_CODE_SUCCESS);
    }

    /**
     * @return （data=data, msg = msg , status = 200)
     */
    public static Result getSuccessResult(Object data, String msg) {
        return getSuccessResult(data, msg, Constants.RESULT_CODE_SUCCESS);
    }

    /**
     * @return （data=data, msg = msg , status = 200)
     */
    public static Result getSuccessResult(Object data, String msg, int code) {
        Result result = new Result();
        result.setData(data);
        result.setStatus(code);
        result.setMessage(msg);
        return result;
    }

    /**
     * @return 默认成功信息 （data=null, msg = FAIL , status = 400)
     */
    public static Result getFailResult() {
        return getFailResult("", Constants.FAIL_MSG, Constants.RESULT_CODE_FAIL);
    }

    /**
     * @return 带数据的成功信息 （data=data, msg = FAIL , status = 400)
     */
    public static Result getFailResult(Object data) {
        return getFailResult(data, Constants.FAIL_MSG, Constants.RESULT_CODE_FAIL);
    }

    /**
     * @return （data=data, msg = msg , status = 400)
     */
    public static Result getFailResult(Object data, String msg) {
        return getFailResult(data, msg, Constants.RESULT_CODE_FAIL);
    }

    /**
     * @return （data=data, msg = msg , status = 400)
     */
    public static Result getFailResult(Object data, String msg, int code) {
        Result result = new Result();
        result.setData(data);
        result.setStatus(code);
        result.setMessage(msg);
        return result;
    }
}