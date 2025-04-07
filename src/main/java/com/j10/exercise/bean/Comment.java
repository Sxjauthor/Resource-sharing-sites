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
 * @since: 2025/3/16 11:30
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("comments")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer mid;
    private Integer rid;
    private String content;
    @TableField(fill = FieldFill.INSERT)
    private String commdate;   //新增评论时 自动填充
    private Integer state;  //逻辑删除字段能不能用待定

    //表外数据
    @TableField(exist = false)
    private String head;
    @TableField(exist = false)
    private String username;

    public Comment(Integer mid, Integer rid, String content, String commdate) {
        this.mid = mid;
        this.rid = rid;
        this.content = content;
        this.commdate = commdate;
    }
}
