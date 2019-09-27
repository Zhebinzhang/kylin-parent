package com.kylin.aop.controller;

import com.kylin.aop.annotation.OperationLogDetail;
import com.kylin.aop.enums.OperationType;
import com.kylin.aop.enums.OperationUnit;
import com.kylin.aop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhebin
 * @Date: 2019/9/26 9:07
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 访问路径 http://localhost:11000/user/findUserNameByTel?tel=1234567
     * @param tel 手机号
     * @return userName
     */
    @RequestMapping("/findUserNameByTel")
    public String findUserNameByTel(@RequestParam("tel") String tel){

        return userService.findUserName(tel);
    }


    @RequestMapping("/test")
    @OperationLogDetail(operationType = OperationType.UPDATE,operationUnit = OperationUnit.USER)
    public String test(){
        return "helloxxxxxx";
    }

}