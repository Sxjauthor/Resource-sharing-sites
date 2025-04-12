package com.j10.exercise.controller;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 10:55
 * 自动填充
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        boolean b = metaObject.hasGetter("joindate");
        if(b){
            metaObject.setValue("joindate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        boolean b1 = metaObject.hasSetter("commdate");
        if(b1){
            metaObject.setValue("commdate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        boolean b2 = metaObject.hasSetter("favdate");
        if(b2){
            metaObject.setValue("favdate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        boolean b3=metaObject.hasSetter("createtime");
        if(b3){
            metaObject.setValue("createtime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
