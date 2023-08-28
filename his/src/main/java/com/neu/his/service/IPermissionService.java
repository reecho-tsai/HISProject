package com.neu.his.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.his.entity.Permission;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: IPermissionService.java
 * @Description: 权限 服务类
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:24:06
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
public interface IPermissionService extends IService<Permission> {
    /**
     * 画出权限树状图
     *
     * @return 树状图对象
     */
    Object listByTree();

    /**
     * 按照id列出权限
     *
     * @param id 用户id
     * @return 对象
     */
    Object userPermissionList(int id);
}
