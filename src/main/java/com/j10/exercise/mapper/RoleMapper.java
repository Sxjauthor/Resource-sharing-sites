package com.j10.exercise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j10.exercise.bean.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 18:01
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    void myInsert(Role role);

}
