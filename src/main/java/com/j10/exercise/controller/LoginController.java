package com.j10.exercise.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.j10.exercise.bean.Member;
import com.j10.exercise.service.MemberService;
import com.j10.exercise.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/8 15:49
 */
@Controller
public class LoginController {
    @Autowired
    private MemberService memberService;

    //登录
    @PostMapping("/login")
    public String login(Member member, String captcha, Model model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = member.getUsername();
        String password = member.getPassword();
        Member success=null;
        //判空
        if(username==null||username.length()==0||password==null||password.length()==0){
            String msg="请输入用户名或密码";
            model.addAttribute("msg",msg);
            return "login";
        }
        //先看验证码
        String code = (String) req.getSession().getAttribute("code");
        if(captcha!=null&&captcha.length()>0){
            if(!captcha.equalsIgnoreCase(code)){
                //验证码不正确
                String msg="验证码不正确";
                model.addAttribute("msg",msg);
                return "login";
            }
        }else{
            //请输入验证码
            String msg="请输入验证码";
            model.addAttribute("msg",msg);
            return "login";
        }
        //查询数据库
        success=memberService.login(member);
        if(success==null){
            //登录失败
            String msg="用户名或密码错误 / 账号不可用";
            model.addAttribute("msg",msg);
            return "login";
        }else{
            //登陆成功
            //cookie 重定向到首页
            String remember = req.getParameter("remember");
            if("1".equals(remember)){
                Cookie yhm=new Cookie("yhm",username);
                yhm.setMaxAge(3600*24*7);
                resp.addCookie(yhm);
            }
            HttpSession session = req.getSession();
            session.setAttribute("member",success);
            return "redirect:/index.html";
        }
    }

    @RequestMapping("/getCookie")
    public void getCookie(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        Cookie[] cookies = req.getCookies();
        if(cookies!=null){
            for (int i = 0; i < cookies.length; i++) {
                if(cookies[i].getName().equals("yhm")){
                    resp.getWriter().write(cookies[i].getValue());
                }
            }
        }
        resp.getWriter().write("");
    }

    //登出
    @GetMapping("/login")
    public String logout(HttpServletRequest req,Model model) throws IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("member");
        session.invalidate();
        model.addAttribute("t",0);
        return "login";
    }
}
