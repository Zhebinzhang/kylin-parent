package com.kylin.aop.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:fdh
 * @Description:
 * @Date： Create in 16:13 2017/12/22
 */
@Configuration
public class senderConfigration {
    /**
     *@Description: 新建队列 topic.messages
     *@Data:16:14 2017/12/22
     */
    @Bean(name = "messages")
    public Queue queueMessages(){
        return new Queue("topic.messages");
    }
    /**
     *@Description: 定义交换器
     *@Data:16:15 2017/12/22
     */
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("exchange");
    }
    /**
     *@Description: 交换机与消息队列进行绑定 队列messages绑定交换机with topic.messages
     *@Data:16:18 2017/12/22
     */
    @Bean
    Binding bindingExchangeMessages(@Qualifier("messages") Queue queueMessages, TopicExchange exchange){
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.messages");
    }
}