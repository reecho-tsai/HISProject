package com.neu.his.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.his.entity.ConstantItem;
import com.neu.his.mapper.ConstantItemMapper;
import com.neu.his.service.IConstantItemService;
import org.springframework.stereotype.Service;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: ConstantItemServiceImpl.java
 * @Description: 状态码表 服务实现类
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:26:26
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@Service
public class ConstantItemServiceImpl extends ServiceImpl<ConstantItemMapper, ConstantItem> implements IConstantItemService {

}
