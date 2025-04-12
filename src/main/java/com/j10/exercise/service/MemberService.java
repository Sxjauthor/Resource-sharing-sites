package com.j10.exercise.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.j10.exercise.bean.Member;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/8 15:50
 */
public interface MemberService extends IService<Member> {
    Member login(Member m);

    Member sign(Member member);
}
