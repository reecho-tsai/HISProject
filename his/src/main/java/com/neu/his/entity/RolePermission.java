package com.neu.his.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: RolePermission.java
 * @Description: 角色权限
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:59:22
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_permission")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 权限ids,以逗号分割
     */
    private String permissionId;
    /**
     * 角色id
     */
    private Integer roleId;

}
