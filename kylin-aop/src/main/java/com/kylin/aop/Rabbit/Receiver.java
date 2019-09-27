package com.kylin.aop.Rabbit;

import com.kylin.aop.model.OperationLog;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @ Author:fdh
 * @ Description: 消息队列监听器
 * @ Date： Create in 14:19 2017/12/22
 */
@Component
public class Receiver {
    @Autowired
    private MongoTemplate mongoTemplate;
    @RabbitListener(queues = "topic.messages")
    public void process2(OperationLog operationLog) throws ClassNotFoundException {
        System.out.println("messages ：" + operationLog);
        System.out.println(Thread.currentThread().getName() + "接收到来自topic.message队列的消息+对象: " + operationLog);
        mongoTemplate.save(operationLog,"operationLog");
    }

}