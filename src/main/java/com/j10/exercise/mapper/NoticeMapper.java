package com.j10.exercise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j10.exercise.bean.Notice;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 19:16
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
}
