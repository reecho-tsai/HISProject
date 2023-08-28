package com.neu.his.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author neu
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("work")
public class Work implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 医生名
     */
    private String name;

    /**
     * 医生类型
     */
    private Integer userType;

    /**
     * 归属科室
     */
    private Integer deptId;


    /**
     * 工作时间
     */
    private String workTime0;

    /**
     * 当前接诊人数
     */
    private Integer numb;

    /**
     * 最大接诊人数
     */
    private Integer numbMax;

    /**
     * 状态
     */
    private Integer state = 1;

}
