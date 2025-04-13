package com.j10.exercise.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.j10.exercise.bean.Member;
import com.j10.exercise.service.MemberService;
import com.j10.exercise.service.impl.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/13 17:23
 */
@Slf4j
@Controller
public class SignController {
    @Autowired
    public MemberService memberService;

    @RequestMapping("/")
    public String signTask() {
        Timer timer = new Timer(true);
        Calendar now=Calendar.getInstance();//当前时间
        now.add(Calendar.DAY_OF_MONTH,1);//当前月份+1天
        now.set(Calendar.HOUR_OF_DAY,0);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);
        Date first=now.getTime();//第一次执行时间 今晚0点
        long period=1000L*3600*24;
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                UpdateWrapper<Member> updateWrapper=new UpdateWrapper<>();
                updateWrapper.set("signflag",0);
                memberService.update(updateWrapper);
            }
        };
        timer.scheduleAtFixedRate(task,first,period);
//        Calendar test=Calendar.getInstance();
//        test.add(Calendar.MINUTE,1);
//        System.out.println(test.getTime()+"---------------");
//        timer.scheduleAtFixedRate(task,test.getTime(),period);
        log.info("定时任务开启");
        return "redirect:/index.html";
    }
}
