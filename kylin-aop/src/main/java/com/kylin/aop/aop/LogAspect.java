package com.kylin.aop.aop;

import com.kylin.aop.model.OperationLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class LogAspect {

    @Autowired
    HttpServletRequest request;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    ServletContext servletContext;



    @Pointcut("@annotation(com.kylin.aop.annotation.OperationLogDetail)")
    public void operationLog(){}



    /**
     * 环绕增强，相当于MethodInterceptor
     */
    @Around("operationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Object res = null;
        long time = System.currentTimeMillis();
        try {
            res =  joinPoint.proceed();
            return res;
        } finally {
            try {
                //方法执行完成后增加日志
                addOperationLog(joinPoint,res);
                System.out.println("around方法结束.....");
            }catch (Exception e){
                System.out.println("LogAspect 操作失败：" + e.getMessage());
                e.printStackTrace();
            }
        }


    }


    private void addOperationLog(JoinPoint joinPoint, Object res) {

        System.out.println("进入方法前执行.....");
          HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        OperationLog operationLog = new OperationLog();

        operationLog.setRequesturl(request.getRequestURL().toString());
        operationLog.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());
        operationLog.setMethodname(joinPoint.getSignature().getName());
        operationLog.setUser(request.getRemoteUser());
        operationLog.setDate(sp.format(new Date()));

        rabbitTemplate.convertAndSend("exchange","topic.messages",operationLog);
    }

    @Before("operationLog()")
    public void doBeforeAdvice(JoinPoint joinPoint){
        System.out.println("Before进入方法前执行.....");

    }




}
