package com.j10.exercise.util;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/3/3 9:21
 * 常量
 */
public class Constants {
    public static final String HEAD_PATH = "http://localhost:9090/files/head"; //文件上传路径
    public static final String DOC_PATH = "http://localhost:9090/files/doc";
    public static final String SOFT_PATH = "http://localhost:9090/files/soft";
    public static final String IMG_PATH = "http://localhost:9090/files/img";
    public static final String ICON_PATH = "http://localhost:9090/files/icon";
    public static final String MEMBER_USERNAME_REG="^[0-9a-zA-Z]{4,8}$";
    public static final String MEMBER_PASSWORD_REG="^[0-9a-zA-Z]{3,6}$";
    public static final String MEMBER_NICK_REG="^[a-zA-Z]+|[\u4e00-\u9fa5]+$";
    public static final String MEMBER_USERNAME_MSG="用户名必须是4~8位,大小写字母和数字的组合";
    public static final String MEMBER_PASSWORD_MSG="密码必须是3~6位,大小写字母和数字的组合";
    public static final String MEMBER_NICK_MSG="昵称必须是中文或英文";
    public static final String PASSWORD_MSG="用户名存在,但密码不正确";
    public static final String DEFAULT_HEAD="head.jpeg";
    public static final String DEFAULT_PASSWORD="000";
}
