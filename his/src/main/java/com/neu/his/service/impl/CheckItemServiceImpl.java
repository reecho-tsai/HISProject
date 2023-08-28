package com.neu.his.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.his.entity.CheckItem;
import com.neu.his.mapper.CheckItemMapper;
import com.neu.his.service.ICheckItemService;
import org.springframework.stereotype.Service;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: CheckItemServiceImpl.java
 * @Description: 检查项目 服务实现类
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:26:10
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@Service
public class CheckItemServiceImpl extends ServiceImpl<CheckItemMapper, CheckItem> implements ICheckItemService {

}
