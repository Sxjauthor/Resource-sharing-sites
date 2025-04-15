package com.j10.exercise.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
public class Manager extends Model<Manager> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private Integer roleid;
    @TableField(fill = FieldFill.INSERT)
    private String createtime;
    @TableField(fill = FieldFill.UPDATE)
    @Version
    private int version; //版本号(实现乐观锁)
    @TableLogic(value = "0",delval = "1")
    @TableField(select = false)
    private Integer deleted; //逻辑删除字段 0未删除 1删除

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

    public Manager(String username, String password, Integer roleid) {
        this.username = username;
        this.password = password;
        this.roleid = roleid;
    }

    public Manager(Integer id, String username, String display) {
        this.id = id;
        this.username = username;
        this.display = display;
    }
}
