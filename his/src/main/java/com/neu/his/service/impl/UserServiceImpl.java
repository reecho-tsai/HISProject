package com.neu.his.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.his.entity.User;
import com.neu.his.mapper.UserMapper;
import com.neu.his.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: UserServiceImpl.java
 * @Description: 用户 服务实现类
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:36:46
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
