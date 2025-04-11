package com.j10.exercise.controller;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j10.exercise.bean.Comment;
import com.j10.exercise.bean.Favorite;
import com.j10.exercise.bean.Member;
import com.j10.exercise.bean.Resource;
import com.j10.exercise.service.CommentService;
import com.j10.exercise.service.MemberService;
import com.j10.exercise.service.ResourceService;
import com.j10.exercise.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 22:23
 */
@Controller
public class ResourceController {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CommentService commentService;

    @RequestMapping("/resload")
    @ResponseBody
    public List<Resource> resload(String type) {
        List<Resource> list;
        if(type.equals("1")){
            list=resourceService.selectR1();
        }else if(type.equals("2")){
            list=resourceService.selectR2();
        }else{
            list=resourceService.selectR3();
        }
        return list;
    }

    @RequestMapping("/resDetail")
    public String detail(String id,String type, Model model) {
        //更改浏览量
        Resource resource=resourceService.getById(Integer.parseInt(id));
        UpdateWrapper<Resource> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id", Integer.parseInt(id)).set("pv", resource.getPv()+1);
        resourceService.update(updateWrapper);
        Resource res=resourceService.selectDetail(resource);
        //查评论
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("rid", Integer.parseInt(id));
        List<Comment> comments=commentService.list(queryWrapper);
        for (Comment comment : comments) {
            QueryWrapper<Member> qw=new QueryWrapper<>();
            qw.eq("id", comment.getMid()).select("username","head");
            Member member=new Member();
            member = member.selectOne(qw);
            comment.setUsername(member.getUsername());
            comment.setHead(member.getHead());
        }
        model.addAttribute("res", res);
        model.addAttribute("list", comments);
        if(type.equals("1")){
            return "r1Detail";
        }else if(type.equals("2")){
            return "r2Detail";
        }else{
            return "r3Detail";
        }
    }

    @RequestMapping("/resDetail/download")
    public void downloadS(String id,String type, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Resource r=new Resource();
        r.setId(Integer.parseInt(id));
        Resource res = resourceService.selectDetail(r);
        //更改下载量
        UpdateWrapper<Resource> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id", Integer.parseInt(id)).set("pv", res.getPv()+1);
        resourceService.update(updateWrapper);
        //扣金币
        Member member = (Member) req.getSession().getAttribute("member");
        System.out.println(member);
        Integer isFree = res.getIsFree();
        //级别优惠
        Integer level = member.getLevel();
        isFree=(isFree-level)>=0?(isFree-level):0;
        member.setGold(member.getGold()-isFree);
        member.insertOrUpdate();
        Member m = member.selectById(member.getId());
        req.getSession().setAttribute("member",m);
        String filename =res.getPath().substring(1,res.getPath().length());
        String path;
        if(type.equals("1")){
            path=Constants.IMG_PATH+"/"+filename;
        }else if(type.equals("2")){
            path = Constants.SOFT_PATH+"/"+ filename;
        }else{
            path=Constants.DOC_PATH+"/"+filename;
        }
        URL url = new URL(path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        String contentType = con.getContentType();
        long length = con.getContentLengthLong();
        resp.setContentType(contentType);
        resp.setContentLengthLong(length);
        resp.setHeader("Content-Disposition","attachment;filename="+ filename);
        IoUtil.copy(con.getInputStream(),resp.getOutputStream());
    }

    @RequestMapping("/resDetail/fav")
    @ResponseBody
    public Resource fav(String id,String i,HttpServletRequest req){
        int i1 = Integer.parseInt(i);
        Resource res=new Resource();
        res.setId(Integer.parseInt(id));
        Resource resource = resourceService.selectDetail(res);
        //更新收藏量
        Member member = (Member) req.getSession().getAttribute("member");
        resourceService.updateFav(member,resource,i1);
        resource = resourceService.selectDetail(res);
        System.out.println(resource);
        return resource;
    }

    @RequestMapping("/resDetail/isFav")
    public void isFav(String id,HttpServletRequest req,HttpServletResponse resp) throws IOException {
        Member member = (Member) req.getSession().getAttribute("member");
        Favorite favorite=new Favorite();
        QueryWrapper<Favorite> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mid", member.getId()).eq("rid", Integer.parseInt(id));
        Favorite fav = favorite.selectOne(queryWrapper);
        if(fav!=null){
            resp.getWriter().write("1");
        }else{
            resp.getWriter().write("0");
        }
    }

}
