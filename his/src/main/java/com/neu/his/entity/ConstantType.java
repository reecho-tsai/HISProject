package com.neu.his.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: ConstantType.java
 * @Description: 状态码类别
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:57:45
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ConstantType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否有效，1 有效，0 失效
     */
    private Integer active;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
