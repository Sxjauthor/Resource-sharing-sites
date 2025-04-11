package com.j10.exercise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j10.exercise.bean.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 22:28
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

    @Select("select supname from supcategory where id=#{value}")
    String selectSupname(Integer id);

    @Select("select subname from subcategory where id=#{value}")
    String selectSubname(Integer id);

}
