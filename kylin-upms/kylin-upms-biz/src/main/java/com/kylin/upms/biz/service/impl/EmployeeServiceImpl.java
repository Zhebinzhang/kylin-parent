package com.kylin.upms.biz.service.impl;

import com.kylin.upms.biz.entity.Employee;
import com.kylin.upms.biz.mapper.EmployeeMapper;
import com.kylin.upms.biz.service.IEmployeeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mht
 * @since 2019-09-15
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
