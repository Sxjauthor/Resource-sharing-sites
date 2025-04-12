package com.j10.exercise.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/3/6 10:31
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("ra")
public class RA extends Model<RA> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer rid;
    private Integer aid;
}
