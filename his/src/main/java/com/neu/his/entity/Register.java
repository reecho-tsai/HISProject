package com.neu.his.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: Register.java
 * @Description: 诊疗信息
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:58:45
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Register implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id病历号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别 0 男 1 女
     */
    private Integer gender;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 挂号级别
     */
    private Integer regsitLevelId;

    /**
     * 挂号科室
     */
    private Integer deptId;

    /**
     * 看诊医生
     */
    private Integer doctorId;

    /**
     * 主诉
     */
    private String readme;

    /**
     * 现病史
     */
    private String present;

    /**
     * 现病史治疗情况
     */
    private String presentTreat;

    /**
     * 既往史
     */
    private String history;

    /**
     * 过敏史
     */
    private String allergy;

    /**
     * 是否要病历本
     */
    private Integer book;

    /**
     * 挂号费用
     */
    private BigDecimal fee;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否有效，1 有效，0 失效
     */
    private Integer active;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
