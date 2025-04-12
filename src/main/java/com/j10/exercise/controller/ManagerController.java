package com.j10.exercise.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.j10.exercise.bean.Manager;
import com.j10.exercise.bean.Role;
import com.j10.exercise.service.ManagerService;
import com.j10.exercise.service.RoleService;
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
@Controller
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/role/managerlist")
    @ResponseBody
    public List<Manager> managerlist() {
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
        return managerList;
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
        updateWrapper.eq("id",manager.getId()).set(StringUtils.hasText(manager.getPassword()),"password",manager.getPassword()).set("roleid",manager.getRoleid());
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
