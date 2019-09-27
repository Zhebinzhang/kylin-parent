package com.kylin.aop.service;

import com.kylin.aop.annotation.OperationLogDetail;

import com.kylin.aop.enums.OperationType;
import com.kylin.aop.enums.OperationUnit;
import org.springframework.stereotype.Service;

/**
 * @Author: zhebin
 * @Date: 2019/9/26 9:08
 */
@Service
public class UserServiceImpl implements UserService {

    @OperationLogDetail(operationUnit = OperationUnit.USER,operationType = OperationType.SELECT)
    @Override
    public String findUserName(String tel) {
        System.out.println("tel:" + tel);
        return "zhangsan";
    }
}
