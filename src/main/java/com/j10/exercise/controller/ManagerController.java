package com.j10.exercise.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j10.exercise.bean.Manager;
import com.j10.exercise.bean.Role;
import com.j10.exercise.service.ManagerService;
import com.j10.exercise.service.RoleService;
import com.j10.exercise.config.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/12 17:37
 */
@Slf4j
@Controller
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/role/managerlist")
    @ResponseBody
    public String managerlist() throws JsonProcessingException {
        String redislist = (String) redisUtil.get("managerlist");
        if(StringUtils.hasText(redislist)){
            log.info("managerlist命中缓存");
            return redislist;
        }
        log.info("managerlist未命中缓存,查询数据库");
        List<Manager> managerList=managerService.list();
        for (int i = 0; i < managerList.size(); i++) {
            Manager manager = managerList.get(i);
            if(manager.getRoleid()!=null){
                Role role=new Role();
                role = role.selectById(manager.getRoleid());
                manager.setRolename(role.getRolename());
                manager.setDisplay(role.getDisplay());
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(managerList);
        redisUtil.set("managerlist",json,20);
        return json;
    }

    @RequestMapping("/role/checkName")
    public void checkName(String username, HttpServletResponse resp) throws IOException {
        //查询管理员用户名是否重复
        QueryWrapper<Manager> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Boolean b = managerService.exists(queryWrapper);
        resp.getWriter().write(String.valueOf(!b));
    }

    @RequestMapping("/role/modifyM")
    public String modifyM(Manager manager, Model model) {
        if(manager.getRoleid()==null){
            manager.setRoleid(0);
        }
        model.addAttribute("manager",manager);
        List<Role> roleList=roleService.list();
        model.addAttribute("roleList",roleList);
        return "admin/ModifyManagerInfo";
    }

    @RequestMapping("/role/updateManager")
    public String updateManager(Manager manager, Model model) {
        UpdateWrapper<Manager> updateWrapper=new UpdateWrapper<>();
        boolean b=manager.getPassword().trim().length()>0;
        updateWrapper.eq("id",manager.getId()).set(b,"password",manager.getPassword()).set("roleid",manager.getRoleid());
        //版本号
        Manager m = managerService.getById(manager.getId());
        Manager m1=new Manager();
        if(StringUtils.hasText(manager.getPassword())||!manager.getRoleid().equals(m.getRoleid())){
            m1.setVersion(m.getVersion());
        }
        managerService.update(m1,updateWrapper);
        model.addAttribute("msg","修改管理员信息成功");
        return "admin/role";
    }

    @RequestMapping("/role/deleteM")
    public String deleteManager(Manager manager, Model model) {
        manager.deleteById();
        model.addAttribute("msg","删除管理员成功");
        return "admin/role";
    }

    @RequestMapping("/role/delsM")
    public String delsM(String list, Model model) {
        String[] split = list.split(",");
        List<Integer> ids=new ArrayList<>();
        for (String s : split) {
            ids.add(Integer.parseInt(s));
        }
        managerService.removeBatchByIds(ids);
        model.addAttribute("msg", "删除管理员成功");
        return "admin/role";
    }

}
