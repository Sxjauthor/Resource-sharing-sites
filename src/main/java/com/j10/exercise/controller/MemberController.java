package com.j10.exercise.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.j10.exercise.bean.Member;
import com.j10.exercise.service.MemberService;
import com.j10.exercise.util.Constants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

    @RequestMapping("/member/sign")
    public String sign(HttpServletRequest req,Model model){
        Member member = (Member) req.getSession().getAttribute("member");
        if(member.getSignflag()==1){
            model.addAttribute("m",1);
            return "index";
        }
        Member m=memberService.sign(member);
        req.getSession().setAttribute("member", m);
        model.addAttribute("m",0);
        return "index";
    }

    @RequestMapping("/member/updateSession")
    public String updateSession(HttpServletRequest req){
        Member member = (Member) req.getSession().getAttribute("member");
        Member member1 = memberService.getById(member.getId());
        req.getSession().setAttribute("member", member1);
        return "info";
    }

    @RequestMapping("/member/modify")
    public String modify(@RequestPart(name = "headFile") MultipartFile head, Member member, HttpServletResponse resp, HttpServletRequest req, Model model) throws IOException {
        Boolean b1=false;Boolean b2=false;Boolean b3=false;
        long size = head.getSize();
        Member m = (Member) req.getSession().getAttribute("member");
        member.setId(m.getId());
        if (size > 0) {//要改头像
            b1=true;
            String contentType = head.getContentType();
            String originName = head.getOriginalFilename();
            String saveName = IdUtil.getSnowflakeNextIdStr() + originName;
            //中文转码
            saveName= URLEncoder.encode(saveName, "UTF-8");
            //保存至数据库
            member.setHead(saveName);
            //上传新头像
            resp.setContentLengthLong(size);
            resp.setContentType(contentType);
            Client client = new Client();
            WebResource webResource = client.resource(Constants.HEAD_PATH + "/" + saveName);
            webResource.put(head.getInputStream());
            //删除旧头像
            String old=m.getHead();
            if(!old.equals(Constants.DEFAULT_HEAD)){
                Client c2=new Client();
                //之前存进session的就是转码后的,这里无需再次转码
                WebResource webResource1 = c2.resource(Constants.HEAD_PATH + "/" + old);
                webResource1.delete();
            }
        }
        if(!m.getNick().equals(member.getNick())){
            b2=true;
        }else{
            member.setNick(null);
        }
        //密码
        if (member.getPassword().trim().length()>0&&!BCrypt.checkpw(member.getPassword(),m.getPassword())) {
            b3=true;
            member.setPassword(BCrypt.hashpw(member.getPassword()));
            memberService.updateById(member);
            req.getSession().removeAttribute("member");
            req.getSession().invalidate();
            model.addAttribute("t",2);
            return "login";
        }else{
            member.setPassword(null);
        }
        if(b1||b2){
            memberService.updateById(member);
            Member member1 = memberService.getById(m.getId());
            req.getSession().setAttribute("member", member1);
            model.addAttribute("t",1);
            return "info";
        }else{
            model.addAttribute("t",2);
            return "info";
        }
    }

}
