package com.neu.his.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: InitUtil.java
 * @Description: 初始化分页信息
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:02:43
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@SuppressWarnings("all")
public class InitUtil {

    /**
     * 初始化分页信息
     */
    public static void initPage(Map<String, Object> param) {
        if (StringUtils.isEmpty(param.get("page"))) {
            param.put("page", 1);
        }
        if (StringUtils.isEmpty(param.get("limit"))) {
            param.put("limit", 3);
        }
    }

    public static void initLike(Map<String, Object> param, QueryWrapper wrapper, String... str) {
        for (String s : str) {
            if (!StringUtils.isEmpty(param.get(s))) {
                wrapper.like(s, param.get(s));
            }
        }
    }

    public static void initEq(Map<String, Object> param, QueryWrapper wrapper, String... str) {
        for (String s : str) {
            if (!StringUtils.isEmpty(param.get(s))) {
                wrapper.eq(s, param.get(s));
            }
        }
    }
}