package com.j10.exercise.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/3/3 8:44
 * 网站注册用户
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("member")
public class Member {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String nick;
    private String head;      //头像文件的存放路径
    private Integer level;
    private Integer gold;
    private String signdate;  //签到日期
    private Integer concount;
    private Integer signflag;
    @TableField(fill = FieldFill.INSERT)
    private String joindate;  //注册日期 新增时自动填充
    private Integer status;   //账号状态  1:可以登录

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
