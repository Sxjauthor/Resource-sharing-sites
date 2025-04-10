package com.j10.exercise.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j10.exercise.bean.Manager;
import com.j10.exercise.bean.Notice;
import com.j10.exercise.service.ManagerService;
import com.j10.exercise.service.NoticeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 19:15
 */
@Controller
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private ManagerService managerService;

    @RequestMapping("/noticeMember")
    @ResponseBody
    public List<Notice> noticeMember() {
        List<Notice> noticeList=noticeService.selectAll();
        return noticeList;
    }

    @RequestMapping("/noticeMem/look")
    public String look(String id) {
        Notice notice = noticeService.getById(Integer.parseInt(id));
        UpdateWrapper<Notice> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",Integer.parseInt(id)).set("look",notice.getLook()+1);
        noticeService.update(updateWrapper);
        return "1";
    }

    @RequestMapping("/noticeMem/detail")
    public String noticeMemDetail(String id, Model model) {
        //1:根据id查询公告
        Notice notice = noticeService.getById(Integer.parseInt(id));
        //2:根据creater查询创建者(管理员username)
        QueryWrapper<Manager> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",notice.getCreater());
        Manager manager = managerService.getOne(queryWrapper);
        if(manager!=null){
            notice.setCusername(manager.getUsername());
        }else{
            notice.setCusername("已注销");
        }
        //3:转发至详情页面
        model.addAttribute("notice",notice);
        return "noticeDetail";
    }

    @RequestMapping("/noticeMem/noticeList")
    public String noticeList(String title,String curpage,Model model) {
        QueryWrapper<Notice> queryWrapper=new QueryWrapper<>();
        Page<Notice> page=new Page<>();
        if(title!=null&&title.trim().length()>0){//设置查询条件
            queryWrapper.like("title",title);
            model.addAttribute("title",title);
        }
        if(curpage!=null&&curpage.length()>0){
            page.setCurrent(Integer.parseInt(curpage));
        }
        //1:查询公告
        page=noticeService.page(page,queryWrapper);
        //2:根据creater查询创建者(管理员username)
        for (Notice notice : page.getRecords()) {
            QueryWrapper<Manager> queryWrapper1=new QueryWrapper<>();
            queryWrapper1.eq("id",notice.getCreater());
            Manager manager = managerService.getOne(queryWrapper1);
            if(manager!=null){
                notice.setCusername(manager.getUsername());
            }else{
                notice.setCusername("已注销");
            }
        }
        //pageList
        List<Long> pageList=new ArrayList<>();
        for (long i = page.getCurrent()-2; i <= page.getCurrent()+2; i++) {
            if(i>=1&&i<=page.getPages()){
                pageList.add(i);
            }
        }
        model.addAttribute("page",page);
        model.addAttribute("pageList",pageList);
        return "more";
    }


}
