package com.neu.his.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.his.entity.Register;
import com.neu.his.mapper.RegisterMapper;
import com.neu.his.service.IRegisterService;
import org.springframework.stereotype.Service;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: RegisterServiceImpl.java
 * @Description: 诊疗信息 服务实现类
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:36:01
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@Service
public class RegisterServiceImpl extends ServiceImpl<RegisterMapper, Register> implements IRegisterService {

}
