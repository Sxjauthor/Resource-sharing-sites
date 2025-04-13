package com.j10.exercise.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j10.exercise.bean.Manager;
import com.j10.exercise.bean.Notice;
import com.j10.exercise.service.ManagerService;
import com.j10.exercise.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public void look(String id) {
        Notice notice = noticeService.getById(Integer.parseInt(id));
        notice.setLook(notice.getLook()+1);
        notice.updateById();
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
        model.addAttribute("title",title);
        model.addAttribute("page",page);
        model.addAttribute("pageList",pageList);
        return "more";
    }

    @RequestMapping("/notice")
    public String notice(String title,String curpage,String status,String start,String end,Model model) {
        QueryWrapper<Notice> queryWrapper=new QueryWrapper<>();
        Page<Notice> page=new Page<>();
        if(curpage!=null&&curpage.length()>0){
            page.setCurrent(Integer.parseInt(curpage));
        }
        page.setSize(7);
        if(status==null||status.trim().length()==0){
            status="0";
        }
        queryWrapper.eq(!status.equals("0"),"status",Integer.parseInt(status)).and(StringUtils.hasText(title),qw->{
            qw.like("title",title).or().like("content",title);
        }).gt(StringUtils.hasText(start),"publishtime", start).
                lt(StringUtils.hasText(end),"publishtime", end).orderByDesc("publishtime");
        //1:查询公告
        page=noticeService.page(page,queryWrapper);
        //2:根据creater查询创建者(管理员username)
        for (Notice notice : page.getRecords()) {
            if(notice.getCreater()!=null){
                QueryWrapper<Manager> queryWrapper1=new QueryWrapper<>();
                queryWrapper1.eq("id",notice.getCreater()).select("username");
                Manager manager=managerService.getOne(queryWrapper1);
                notice.setCusername(manager.getUsername());
            }else {
                notice.setCusername("已注销");
            }
        }
        List<Long> pageList=new ArrayList<>();
        for (long i = page.getCurrent()-2; i <= page.getCurrent()+2; i++) {
            if(i>=1&&i<=page.getPages()){
                pageList.add(i);
            }
        }
        model.addAttribute("title",title);
        model.addAttribute("status",status);
        model.addAttribute("start",start);
        model.addAttribute("end",end);
        model.addAttribute("page",page);
        model.addAttribute("pageList",pageList);
        return "admin/noticeManager";
    }

    @RequestMapping("/notice/add")
    public String noticeAdd(String title, String content, HttpServletRequest req,Model model) {
        Manager manager = (Manager) req.getSession().getAttribute("manager");
        Notice notice=new Notice(title,content,manager.getId());
        notice.insert();
        model.addAttribute("msg","新增公告成功");
        return "forward:/notice";
    }

    @RequestMapping("/notice/opt")
    public String opt(Notice notice,Model model){
        if(notice.getStatus().equals(2)){
            String publishtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            notice.setPublishtime(publishtime);
            notice.updateById();
            model.addAttribute("msg","发布成功");
        }else if(notice.getStatus().equals(3)){
            notice.updateById();
            model.addAttribute("msg","下架成功");
        }
        return "forward:/notice";
    }

    @RequestMapping("/notice/modify")
    public String modify(String id,Model model){
        Notice notice = noticeService.getById(Integer.parseInt(id));
        model.addAttribute("notice",notice);
        return "admin/noticeModify.html";
    }

    @RequestMapping("/notice/modifyN")
    public String modifyN(Notice notice,Model model){
        notice.updateById();
        model.addAttribute("msg","修改成功");
        return "forward:/notice";
    }

    @RequestMapping("/notice/detailM")
    public String detailM(Notice notice,Model model){
        //1:根据id查询公告
        notice = notice.selectById();
        //2:根据creater查询创建者(管理员username)
        if(notice.getCreater()!=null){
            Manager manager=managerService.getById(notice.getCreater());
            notice.setCusername(manager.getUsername());
        }else {
            notice.setCusername("已注销");
        }
        //3:转发至详情页面
        model.addAttribute("notice",notice);
        return"admin/noticeDetailManager";
    }

    @RequestMapping("/notice/delete")
    public String delete(String list,Model model){
        List<Integer> ids=new ArrayList<>();
        String[] split = list.split(",");
        for (String s : split) {
            ids.add(Integer.parseInt(s));
        }
        UpdateWrapper<Notice> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("status",3).in("id",ids);
        noticeService.update(updateWrapper);
        model.addAttribute("msg","下架成功");
        return "forward:/notice";
    }
}
