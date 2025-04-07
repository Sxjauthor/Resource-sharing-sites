package com.j10.exercise.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/3/6 10:30
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("action")
public class Action {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String actionname;
    private String actionurl;

    //指示当前角色是否有该能力
    @TableField(exist = false)
    private boolean have;  //表中没有该字段
}
