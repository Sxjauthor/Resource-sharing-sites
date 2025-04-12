package com.j10.exercise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j10.exercise.bean.Action;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/12 15:35
 */
@Mapper
public interface ActionMapper extends BaseMapper<Action> {
    @Select("select a.* from ra left join action a on ra.aid=a.id where rid=#{value}")
    List<Action> selectAction(Integer roleid);
}
