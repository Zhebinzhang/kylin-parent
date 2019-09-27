package com.kylin.upms.biz.exception;


import com.kylin.upms.biz.vo.ResEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHadler {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResEntity result(Exception e){
        //开发环境使用debug 记录sql   生产开启 info  是业务类型的日志    error :
        logger.error(e.getMessage());
       // logger.info("订单开始,,,,, 商品为{},单价为：{}，数量为：{},折扣为：{}，订单号为:{}",1,2,3,4,5);
        return ResEntity.error("服务器开小差了",e.getMessage());

    }
}
