package com.neu.his.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @author msprc
 * @version v1.0.0
 * @ClassName CheckItem.java
 * @Description 检查项目
 * @date 2022年12月6日 下午2:57:05
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CheckItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 检查名称
     */
    private String name;

    /**
     * 检查内容
     */
    private String content;

    /**
     * 检查费用
     */
    private BigDecimal fee;

    /**
     * 是否有效，1 有效，0 失效
     */
    private Integer active;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
