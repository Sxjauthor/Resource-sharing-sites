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
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
