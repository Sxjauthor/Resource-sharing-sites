package com.j10.exercise.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j10.exercise.bean.Comment;
import com.j10.exercise.bean.Member;
import com.j10.exercise.bean.Resource;
import com.j10.exercise.service.CommentService;
import com.j10.exercise.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/11 14:30
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/resDetail/publish")
    public String resDetailPublish(String rid, String type, String comment, HttpServletRequest req, Model model) {
        Member member = (Member) req.getSession().getAttribute("member");
        Comment c=new Comment(member.getId(),Integer.parseInt(rid),comment);
        commentService.saveOrUpdate(c);
        model.addAttribute("msg","发表成功");
        //查评论
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("rid", Integer.parseInt(rid));
        List<Comment> comments=commentService.list(queryWrapper);
        for (Comment c2 : comments) {
            QueryWrapper<Member> qw=new QueryWrapper<>();
            qw.eq("id", c2.getMid()).select("username","head");
            Member m=new Member();
            m = m.selectOne(qw);
            c2.setUsername(m.getUsername());
            c2.setHead(m.getHead());
        }
        req.setAttribute("list",comments);
        Resource r=new Resource();
        r.setId(Integer.parseInt(rid));
        Resource res = resourceService.selectDetail(r);
        model.addAttribute("res",res);
        if(type.equals("1")){
            return "r1Detail";
        }else if(type.equals("2")){
            return "r2Detail";
        }else{
            return "r3Detail";
        }
    }

    @RequestMapping("/res/comment")
    public String imgComment(String id,String content,String status,String cur,Model model) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        if (status == null) {
            status = "0";
        }
        Integer state = Integer.parseInt(status);
        Integer rid = Integer.parseInt(id);
        queryWrapper.eq("rid", rid).eq(!status.equals("0"),"state",state).
                like(StringUtils.hasText(content), "content", content);
        Page<Comment> page = new Page<>();
        if (cur != null && cur.trim().length() > 0) {
            page.setCurrent(Integer.parseInt(cur));
        }
        page.setSize(7);
        page = commentService.page(page, queryWrapper);
        List<Long> pageList = new ArrayList<>();
        for (long i = page.getCurrent() - 2; i <= page.getCurrent() + 2; i++) {
            if (i > 0 && i <= page.getPages()) {
                pageList.add(i);
            }
        }
        model.addAttribute("content", content);
        model.addAttribute("status", status);
        model.addAttribute("id", id);
        model.addAttribute("page", page);
        model.addAttribute("pageList", pageList);
        return "admin/comment";
    }

    @RequestMapping("/res/banComment")
    public String imgBanComment(String id,String cid,Model model) {
        UpdateWrapper<Comment> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", cid).set("state", 3);
        commentService.update(updateWrapper);
        model.addAttribute("msg","评论禁用成功");
        model.addAttribute("id",Integer.parseInt(id));
        return "forward:/res/comment";
    }

    @RequestMapping("/res/delComment")
    public String imgDelComment(String id,String cid,Model model) {
        UpdateWrapper<Comment> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", cid).set("state", 2);
        commentService.update(updateWrapper);
        model.addAttribute("msg","评论删除成功");
        model.addAttribute("id",Integer.parseInt(id));
        return "forward:/res/comment";
    }
}
