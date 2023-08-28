package com.neu.his.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: User.java
 * @Description: 用户
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:59:32
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 电话号码
     */
    private String telephone;

    /**
     * 医生类型
     */
    private Integer userType;

    /**
     * 归属科室
     */
    private Integer deptId;


    /**
     * 最后登录时间
     */
    private LocalDateTime lastLogin;

    /**
     * 是否有效，1 有效，0 失效
     */
    private Integer active;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 工作时间
     */
    private String workTime;

    /**
     * 将user2的值赋给user1的空值
     *
     * @param user1 被赋值的对象
     * @param user2 数据来源
     */
    public static void compAndSet(User user1, User user2) {
        if (user1.id == null) {
            user1.id = user2.id;
        }
        if (user1.password == null) {
            user1.password = user2.password;
        }
        if (user1.username == null) {
            user1.username = user2.username;
        }
        if (user1.realName == null) {
            user1.realName = user2.realName;
        }
        if (user1.telephone == null) {
            user1.telephone = user2.telephone;
        }
        if (user1.userType == null) {
            user1.userType = user2.userType;
        }
        if (user1.active == null) {
            user1.active = user2.active;
        }
        if (user1.lastLogin == null) {
            user1.lastLogin = user2.lastLogin;
        }
        if (user1.deptId == null) {
            user1.deptId = user2.deptId;
        }
        if (user1.workTime == null) {
            user1.workTime = user2.workTime;
        }
        if (user1.createTime == null) {
            user1.createTime = user2.createTime;
        }

    }
}
