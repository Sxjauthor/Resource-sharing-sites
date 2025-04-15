package com.j10.exercise.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j10.exercise.bean.Comment;
import com.j10.exercise.bean.Favorite;
import com.j10.exercise.bean.Member;
import com.j10.exercise.bean.Resource;
import com.j10.exercise.service.CommentService;
import com.j10.exercise.service.ResourceService;
import com.j10.exercise.util.Constants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public void download(String id,String type, HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    @RequestMapping("/resDetail/search")
    public String search(String search, String type, Model model){
        if(type==null||type.trim().length()==0){//默认查软件
            type="2";
        }
        Resource r=new Resource();
        r.setType(Integer.parseInt(type));
        List<Resource> list=resourceService.search(r,search);
        model.addAttribute("list", list);
        model.addAttribute("search", search);
        model.addAttribute("type", type);
        return "search";
    }

    @RequestMapping("/resDetail/moreW")
    public String moreW(Model model){
        Resource r=new Resource();
        r.setType(1);
        List<Resource> list=resourceService.search(r,null);
        model.addAttribute("list", list);
        model.addAttribute("type", "1");
        return "search";
    }

    @RequestMapping("/resDetail/myFav")
    public String myFav(String type,HttpServletRequest req,Model model){
        Member member = (Member) req.getSession().getAttribute("member");
        if(type==null){
            type="2";
        }
        Favorite favorite=new Favorite();
        QueryWrapper<Favorite> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mid", member.getId());
        List<Favorite> favoriteList = favorite.selectList(queryWrapper);
        List<Integer> list=new ArrayList<>();
        for (int i = 0; i < favoriteList.size(); i++) {
            list.add(favoriteList.get(i).getRid());
        }
        List<Resource> resources =null;
        if(list.size()>0){
            QueryWrapper<Resource> queryWrapper2=new QueryWrapper<>();
            queryWrapper2.eq("type", Integer.parseInt(type)).eq("status",1).in("id",list);
            resources = resourceService.list(queryWrapper2);
            if(resources!=null&&resources.size()>0){
                for (Resource resource : resources) {
                    resource.setJoindate(resource.getJoindate().substring(5,10));
                }
            }
        }
        req.setAttribute("list",resources);
        req.setAttribute("type",type);
        return "myFav";
    }

    @RequestMapping("/resDetail/upload")
    public String upload(MultipartFile res,MultipartFile img,Resource r,
                         HttpServletRequest req,HttpServletResponse resp,Model model) throws IOException {
        //res上传
        String name1 = res.getOriginalFilename();
        if(name1.length()>14){
            r.setResname(name1.substring(0,15));
        }else{
            r.setResname(name1);
        }
        long size1 = res.getSize();
        String contentType1 = res.getContentType();
        System.out.println(contentType1);
        String encode1 = URLEncoder.encode(name1, "utf-8");
        if(encode1.length()>25){
            String start= encode1.substring(0,10);
            String end= encode1.substring(encode1.length()-15, encode1.length());
            encode1 =start+end;
        }
        Integer ct1 =null;
        r.setPath("/"+ encode1);
        String path1 =null;
        if(contentType1.equals("image/png")|| contentType1.equals("image/jpeg")){
            path1 =Constants.IMG_PATH+"/"+ encode1;
            ct1 =1;
        }else if(contentType1.equals("application/zip")|| contentType1.equals("application/x-msdownload")){
            path1 =Constants.SOFT_PATH+"/"+ encode1;
            ct1 =2;
        }else if(contentType1.equals("application/octet-stream")|| contentType1.equals("application/msword")||
                contentType1.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")||
                contentType1.equals("application/pdf")|| contentType1.equals("text/plain")|| contentType1.equals("application/x-zip-compressed")){
            path1 =Constants.DOC_PATH+"/"+ encode1;
            ct1 =3;
        }
        r.setContentType(ct1);
        resp.setContentType(contentType1);
        resp.setContentLengthLong(size1);
        Client client1=new Client();
        WebResource webResource1 = client1.resource(path1);
        webResource1.put(res.getInputStream());
        //img上传
        String name2 = img.getOriginalFilename();
        long size2 = img.getSize();
        String contentType2 = img.getContentType();
        String savename= IdUtil.getSnowflakeNextIdStr().substring(0,10)+ name2;
        String encode2 = URLEncoder.encode(savename, "utf-8");
        if(encode2.length()>30){
            String start= encode2.substring(0,10);
            String end= encode2.substring(encode2.length()-20, encode2.length());
            encode2 =start+end;
        }
        r.setThumbnail("/"+ encode2);
        String path2 =null;
        if(contentType2.equals("image/png")|| contentType2.equals("image/jpeg")){
            path2 =Constants.ICON_PATH+"/"+ encode2;
        }
        resp.setContentType(contentType2);
        resp.setContentLengthLong(size2);
        Client client=new Client();
        WebResource webResource = client.resource(path2);
        webResource.put(img.getInputStream());
        System.out.println("icon");
        String path3 =Constants.IMG_PATH+"/"+ encode2;
        WebResource webResource2 = client.resource(path3);
        webResource2.put(img.getInputStream());
        System.out.println("img");
        //上传数据库
        Member member = (Member) req.getSession().getAttribute("member");
        r.setUploader(member.getId());
        boolean b=resourceService.share(r);
        if(b){
            model.addAttribute("msg","上传成功,审核中...");
        }
        return "index";
    }

    @RequestMapping("/res/img")
    public String r1List(String resname,String status,String cur,Model model) {
        if(status==null){
            status="0";
        }
        QueryWrapper<Resource> queryWrapper=new QueryWrapper<>();
        Integer state = Integer.parseInt(status);
        queryWrapper.eq("type",1).eq(!status.equals("0"),"status",state).
                and(StringUtils.hasText(resname),qw->{
            qw.like("resname",resname).or().like("display",resname);
        });
        Page<Resource> page=new Page<>();
        if(cur!=null&&cur.trim().length()>0){
            page.setCurrent(Integer.parseInt(cur));
        }
        page.setSize(7);
        page = resourceService.page(page, queryWrapper);
        List<Long> pageList=new ArrayList<>();
        for (long i = page.getCurrent()-2; i <= page.getCurrent()+2; i++) {
            if(i>0&&i<=page.getPages()){
                pageList.add(i);
            }
        }
        model.addAttribute("resname",resname);
        model.addAttribute("status",status);
        model.addAttribute("page", page);
        model.addAttribute("pageList",pageList);
        return "admin/r1List";
    }

    @RequestMapping("/res/ban")
    public String ban(String id,String type,Model model) {
        UpdateWrapper<Resource> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("status",2);
        resourceService.update(updateWrapper);
        model.addAttribute("msg","资源禁用成功");
        if(type.equals("1")){
            return "forward:/res/img";
        }else if(type.equals("2")){
            return "forward:/res/soft";
        }else{
            return "forward:/res/doc";
        }
    }

    @RequestMapping("/res/recover")
    public String imgRecover(String id,String type,Model model) {
        UpdateWrapper<Resource> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("status",1);
        resourceService.update(updateWrapper);
        model.addAttribute("msg","资源恢复成功");
        if(type.equals("1")){
            return "forward:/res/img";
        }else if(type.equals("2")){
            return "forward:/res/soft";
        }else{
            return "forward:/res/doc";
        }
    }

    @RequestMapping("/res/pass")
    public String imgPass(String id,String type,Model model) {
        UpdateWrapper<Resource> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("status",1);
        resourceService.update(updateWrapper);
        Resource resource = resourceService.getById(Integer.parseInt(id));
        //会员加金币
        if(resource.getUploader()!=null){
            QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",resource.getUploader());
            Member member=new Member();
            Member mem = member.selectOne(queryWrapper);
            UpdateWrapper<Member> uw=new UpdateWrapper<>();
            if(resource.getType()==1){//壁纸2
                uw.eq("id",mem.getId()).set("gold",mem.getGold()+2);
            }else if(resource.getType()==2){//软件5
                uw.eq("id",mem.getId()).set("gold",mem.getGold()+5);
            }else if(resource.getType()==3){//资料3
                uw.eq("id",mem.getId()).set("gold",mem.getGold()+3);
            }
            mem.update(uw);
        }
        model.addAttribute("msg","资源审核通过");
        if(type.equals("1")){
            return "forward:/res/img";
        }else if(type.equals("2")){
            return "forward:/res/soft";
        }else{
            return "forward:/res/doc";
        }
    }

    @RequestMapping("/res/soft")
    public String r2List(String resname,String status,String cur,Model model) {
        if(status==null){
            status="0";
        }
        QueryWrapper<Resource> queryWrapper=new QueryWrapper<>();
        Integer state = Integer.parseInt(status);
        queryWrapper.eq("type",2).eq(!status.equals("0"),"status",state).
                and(StringUtils.hasText(resname),qw->{
                    qw.like("resname",resname).or().like("display",resname);
                });
        Page<Resource> page=new Page<>();
        if(cur!=null&&cur.trim().length()>0){
            page.setCurrent(Integer.parseInt(cur));
        }
        page.setSize(7);
        page = resourceService.page(page, queryWrapper);
        List<Long> pageList=new ArrayList<>();
        for (long i = page.getCurrent()-2; i <= page.getCurrent()+2; i++) {
            if(i>0&&i<=page.getPages()){
                pageList.add(i);
            }
        }
        model.addAttribute("resname",resname);
        model.addAttribute("status",status);
        model.addAttribute("page", page);
        model.addAttribute("pageList",pageList);
        return "admin/r2List";
    }

    @RequestMapping("/res/doc")
    public String r3List(String resname,String status,String cur,Model model) {
        if(status==null){
            status="0";
        }
        QueryWrapper<Resource> queryWrapper=new QueryWrapper<>();
        Integer state = Integer.parseInt(status);
        queryWrapper.eq("type",3).eq(!status.equals("0"),"status",state).
                and(StringUtils.hasText(resname),qw->{
                    qw.like("resname",resname).or().like("display",resname);
                });
        Page<Resource> page=new Page<>();
        if(cur!=null&&cur.trim().length()>0){
            page.setCurrent(Integer.parseInt(cur));
        }
        page.setSize(7);
        page = resourceService.page(page, queryWrapper);
        List<Long> pageList=new ArrayList<>();
        for (long i = page.getCurrent()-2; i <= page.getCurrent()+2; i++) {
            if(i>0&&i<=page.getPages()){
                pageList.add(i);
            }
        }
        model.addAttribute("resname",resname);
        model.addAttribute("status",status);
        model.addAttribute("page", page);
        model.addAttribute("pageList",pageList);
        return "admin/r3List";
    }
}
