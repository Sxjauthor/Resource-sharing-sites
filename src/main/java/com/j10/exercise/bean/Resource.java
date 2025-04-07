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
 * @since: 2025/3/14 8:12
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("resource")
public class Resource {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String resname;
    private String path;
    private String thumbnail;
    @TableField(fill = FieldFill.INSERT)
    private String joindate;
    private String display;
    private Integer uploader;
    private Integer status;
    // TODO: 2025/4/7 使用Redis缓存浏览量,收藏量,下载量
    private Integer pv; //浏览量
    private Integer fav;
    private Integer down;
    private Integer type;
    private Integer isFree;
    private Integer sub;

    //其他表的
    @TableField(exist = false)
    private String supname;//大分类名
    @TableField(exist = false)
    private String subname;//小分类名
    @TableField(exist = false)
    private Integer contentType;
}
