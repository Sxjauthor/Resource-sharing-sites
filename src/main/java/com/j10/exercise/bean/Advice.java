package com.j10.exercise.bean;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/14 11:20
 */
@Slf4j
@Component
@Aspect
public class Advice {
    @Pointcut("execution(String com.j10.exercise.controller.ManagerController.managerlist(..))")
    public void pt1() {}

    @Pointcut("execution(String com.j10.exercise.controller.ResourceController.upload(..))")
    public void pt2() {}

    @Before("pt1()")
    public void before() {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        log.info("开始时间: "+time);
    }

    @After("pt1()")
    public void after() {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        log.info("结束时间: "+time);
    }

    @Around("pt2()")
    public void around(ProceedingJoinPoint pt){
        log.info("环绕通知开始："+ DateUtil.now());
        Object[] args = pt.getArgs();
        log.info(Arrays.toString(args));
        try {
            pt.proceed();
        } catch (Throwable e) {
            log.info("环绕通知：出异常啦！");
            throw new RuntimeException(e);
        }
        log.info("环绕通知结束："+ DateUtil.now());
    }
}
