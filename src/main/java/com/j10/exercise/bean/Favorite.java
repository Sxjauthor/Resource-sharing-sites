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
 * @since: 2025/4/11 17:27
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("favorite")
public class Favorite extends Model<Favorite> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer mid;
    private Integer rid;
    @TableField(fill= FieldFill.INSERT)
    private String favdate;
}
