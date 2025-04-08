package com.j10.exercise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j10.exercise.bean.Member;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/8 15:52
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    Member checkUsername(Member m);
}
