package com.j10.exercise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j10.exercise.bean.Manager;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 17:01
 */
@Mapper
public interface ManagerMapper extends BaseMapper<Manager> {
}
