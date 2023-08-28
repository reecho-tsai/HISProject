package com.neu.his.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.his.entity.Permission;
import com.neu.his.entity.RolePermission;
import com.neu.his.mapper.PermissionMapper;
import com.neu.his.service.IPermissionService;
import com.neu.his.service.IRolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: PermissionServiceImpl.java
 * @Description: 权限 服务实现类
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:27:28
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Resource
    private IRolePermissionService roles;

    /**
     * @Function: PermissionServiceImpl.java
     * @Description: 树状图展示当前权限
     * @return：权限树图
     * @version: v1.0.0
     * @author: msprc
     * @date: 2022年12月6日 下午3:35:14
     * <p>
     * Modification History:
     * Date         Author          Version            Description
     * ---------------------------------------------------------*
     * 2022年12月6日     msprc           v1.0.0               修改原因
     * @see com.neu.his.service.IPermissionService#listByTree()
     */
    @Override
    public Object listByTree() {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", 0);
        List<Permission> list = this.list(wrapper);
        for (Permission i : list) {
            getChildren(i);
        }
        return list;
    }

    /**
     * @Function: PermissionServiceImpl.java
     * @Description: 通过父权限获取子权限, 这里假设为2级
     * @param: 父权限对象
     * @return：子权限对象
     * @version: v1.0.0
     * @author: msprc
     * @date: 2022年12月6日 下午3:34:35
     * <p>
     * Modification History:
     * Date         Author          Version            Description
     * ---------------------------------------------------------*
     * 2022年12月6日     msprc           v1.0.0               修改原因
     */
    private void getChildren(Permission permission) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", permission.getId());
        // List<Permission> list =this.list(wrapper) ;
        // for (Permission i : list) {
        //   if (permission.getType() != 0) {
        //     getChildren(permission);
        //   }
        // }
        permission.setChildren(this.list(wrapper));
    }

    @Override
    public Object userPermissionList(int id) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", id);
        RolePermission rop = roles.getOne(wrapper, false);
        if (rop == null) {
            return null;
        }
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0).inSql("id", rop.getPermissionId());
        List<Permission> list = this.list(queryWrapper);
        for (Permission i : list) {
            queryWrapper.clear();
            queryWrapper.eq("parent_id", i.getId()).inSql("id", rop.getPermissionId());
            i.setChildren(this.list(queryWrapper));
        }
        return list;
    }

}
