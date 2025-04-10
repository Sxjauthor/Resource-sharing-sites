package com.j10.exercise.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j10.exercise.bean.Member;
import com.j10.exercise.exception.CustomerException;
import com.j10.exercise.mapper.MemberMapper;
import com.j10.exercise.service.MemberService;
import com.j10.exercise.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/8 15:51
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member login(Member m) {
        //1:检查数据库记录是否有该用户名  2:进行密码验证
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", m.getUsername());
        Member member = m.selectOne(queryWrapper);
        if(member == null){
            return null;
        }else{
            if (BCrypt.checkpw(m.getPassword(), member.getPassword())) {
                return member;
            }else{
                throw new CustomerException(Constants.PASSWORD_MSG, 500);
            }
        }
    }

}
