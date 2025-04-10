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
 * @since: 2025/3/10 19:20
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("notice")
public class Notice extends Model<Notice> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private Integer creater;  //创建人id
    @TableField(fill = FieldFill.INSERT)
    private String createtime;
    private String publishtime;
    private Integer status;   //状态 1:未发布 2:发布 3:下架
    // TODO: 2025/4/7 使用redis缓存浏览量
    private Integer look;

    //表外数据(发表日期)
    @TableField(exist = false)
    private String day;
    @TableField(exist = false)
    private String ym;
    @TableField(exist = false)
    private String cusername; //创建者用户名

    public Notice(Integer id, String publishtime) {
        this.id = id;
        this.publishtime = publishtime;
    }

    public Notice(String title, String content, String createtime, Integer creater) {
        this.title = title;
        this.content = content;
        this.createtime = createtime;
        this.creater = creater;
    }
}
