package com.j10.exercise.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/3/6 9:50
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("manager")
public class Manager {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private Integer roleid;
    @TableField(fill = FieldFill.INSERT)
    private String createtime;

    //非manager表里的信息
    @TableField(exist = false)
    private String rolename;
    @TableField(exist = false)
    private String display;
    //对应的操作资源
    @TableField(exist = false)
    private List<Action> actionList;

    public Manager(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Manager(String username, String password, Integer roleid, String createtime) {
        this.username = username;
        this.password = password;
        this.roleid = roleid;
        this.createtime = createtime;
    }

    public Manager(Integer id, String username, String createtime, String display) {
        this.id = id;
        this.username = username;
        this.createtime = createtime;
        this.display = display;
    }
}
