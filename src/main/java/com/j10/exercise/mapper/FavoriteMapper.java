package com.j10.exercise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j10.exercise.bean.Favorite;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/11 17:30
 */
@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {
}
