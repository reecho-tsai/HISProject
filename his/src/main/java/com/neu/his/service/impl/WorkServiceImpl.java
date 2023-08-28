package com.neu.his.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.his.entity.Work;
import com.neu.his.mapper.WorkMapper;
import com.neu.his.service.IWorkService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 排班 服务实现类
 * </p>
 *
 * @author neu
 */
@Service
public class WorkServiceImpl extends ServiceImpl<WorkMapper, Work> implements IWorkService {

}
