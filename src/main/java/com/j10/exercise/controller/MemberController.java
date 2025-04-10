package com.j10.exercise.controller;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.j10.exercise.bean.Member;
import com.j10.exercise.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 10:15
 */
@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    //验证用户名
    @GetMapping("/register")
    public void verifyUsername(Member member, HttpServletResponse resp) throws IOException {
        Member exist= null;
        QueryWrapper<Member> wrapper = new QueryWrapper<>(member);
        exist = member.selectOne(wrapper);
        if(exist!=null){
            resp.getWriter().write("true");
        }else{
            resp.getWriter().write("false");
        }
    }

    @PostMapping("/register")
    public String register(Member member, Model model){
        member.setPassword(BCrypt.hashpw(member.getPassword()));
        memberService.save(member);
        model.addAttribute("t",1);
        return "login";
    }
}
