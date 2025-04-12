package com.j10.exercise.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j10.exercise.bean.Member;
import com.j10.exercise.exception.CustomerException;
import com.j10.exercise.mapper.MemberMapper;
import com.j10.exercise.service.MemberService;
import com.j10.exercise.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/8 15:51
 */
@Slf4j
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

    @Override
    public Member sign(Member member) {
        //1:判断signdate是不是昨天
        //是=>concount+1  否则 concount=1
        //设置concount  设置signdate为今天
        String signdate = member.getSigndate();
        Calendar now = Calendar.getInstance();//当前时间
        now.set(Calendar.HOUR_OF_DAY,0);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);
        now.add(Calendar.DAY_OF_MONTH,-1);//昨天
        String yesterday=new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());
        if(yesterday.equals(signdate)){
            member.setConcount(member.getConcount()+1);
        }else{
            member.setConcount(1);
        }
        //2:判断concount
        //1-3=>gold+1  4-6=>gold+2  7-9=>gold+3  10=>5
        if(member.getConcount()>=1&&member.getConcount()<=3){
            member.setGold(member.getGold()+1);
        }else if(member.getConcount()>=4&&member.getConcount()<=6){
            member.setGold(member.getGold()+2);
        }else if(member.getConcount()>=7&&member.getConcount()<=9){
            member.setGold(member.getGold()+3);
        }else if(member.getConcount()>=10){
            member.setGold(member.getGold()+5);
        }
        //根据连续签到天数 更新级别
        Integer level=member.getConcount()/10+1;
        member.setLevel(level);
        //3:设置signdate为今天  signflag为1
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        member.setSigndate(today);
        member.setSignflag(1);
        memberMapper.updateById(member);
        //查询新的member并返回
        Member m = memberMapper.selectById(member.getId());
        return m;
    }

}
