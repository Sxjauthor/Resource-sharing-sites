package com.j10.exercise.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/3/6 10:29
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("role")
public class Role extends Model<Role> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String rolename;
    private String display;
    @TableField(fill = FieldFill.INSERT)
    private String createtime;

    public Role(Integer id, String rolename, String display) {
        this.id = id;
        this.rolename = rolename;
        this.display = display;
    }

    public Role(String rolename, String display) {
        this.rolename = rolename;
        this.display = display;
    }
}
