package com.neu.his.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.his.entity.ConstantType;
import com.neu.his.mapper.ConstantTypeMapper;
import com.neu.his.service.IConstantTypeService;
import org.springframework.stereotype.Service;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: ConstantTypeServiceImpl.java
 * @Description: 状态码类别 服务实现类
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:26:38
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@Service
public class ConstantTypeServiceImpl extends ServiceImpl<ConstantTypeMapper, ConstantType> implements IConstantTypeService {

}
