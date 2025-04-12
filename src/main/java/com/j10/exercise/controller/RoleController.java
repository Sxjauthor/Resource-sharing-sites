package com.j10.exercise.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j10.exercise.bean.Action;
import com.j10.exercise.bean.Manager;
import com.j10.exercise.bean.Role;
import com.j10.exercise.mapper.ActionMapper;
import com.j10.exercise.service.ActionService;
import com.j10.exercise.service.RoleService;
import com.j10.exercise.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/12 15:05
 */
@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private ActionService actionService;

    @RequestMapping("/role/rolelist")
    public String rolelist(Model model) {
        List<Role> roleList=roleService.list();
        model.addAttribute("roleList",roleList);
        return "forward:/role";
    }

    @RequestMapping("/role/roleAddPre")
    public String roleAddPre(Model model) {
        Action action=new Action();
        List<Action> actions=action.selectAll();
        model.addAttribute("actions",actions);
        return "forward:/admin/roleadd.html";
    }

    @RequestMapping("/role/roleAdd")
    public String roleAdd(String rolename,String display,String[] actions,Model model) {
        //新增角色
        Role role=new Role(rolename,display);
        ArrayList<Integer> aids= new ArrayList<>();
        for (int i = 0; i < actions.length; i++) {
            aids.add(Integer.parseInt(actions[i]));
        }
        roleService.addRole(role,aids);
        model.addAttribute("msg","新增角色成功");
        List<Role> roleList=roleService.list();
        model.addAttribute("roleList",roleList);
        return "forward:/role";
    }

    @RequestMapping("/role/checkRoleName")
    public void checkRoleName(String rolename, HttpServletResponse resp) throws IOException {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("rolename",rolename);
        boolean b = roleService.exists(queryWrapper);
        resp.getWriter().write(String.valueOf(!b));
    }

    @RequestMapping("/role/actions")
    public String actions(String roleid,String rolename,String display,Model model) {
        List<Action> actions=actionService.selectAction(Integer.parseInt(roleid));
        model.addAttribute("actions",actions);
        model.addAttribute("roleid",roleid);
        model.addAttribute("rolename",rolename);
        model.addAttribute("display",display);
        return "forward:/admin/roleActionInfo.html";
    }

    @RequestMapping("/role/updateRole")
    public String updateRole(String roleid,String rolename,String display,String[] actions,Model model) {
        Role role=new Role(Integer.parseInt(roleid),rolename,display);
        List<Integer> aids =new ArrayList<>();
        for (String action : actions) {
            aids.add(Integer.parseInt(action));
        }
        roleService.updateRole(role, aids);
        //转发回roleActionInfo.jsp 浏览器重新加载数据 需重新查询数据
        List<Action> acts = actionService.selectAction(Integer.parseInt(roleid));
        model.addAttribute("actions",acts);
        model.addAttribute("roleid",roleid);
        model.addAttribute("rolename",rolename);
        model.addAttribute("display",display);
        model.addAttribute("msg", "修改成功");
        return "forward:/admin/roleActionInfo.html";
    }

    @RequestMapping("/role/delRole")
    public String delRole(String id,Model model) {
        Role role=new Role();
        role.setId(Integer.parseInt(id));
        role.deleteById();
        model.addAttribute("msg","删除角色成功");
        List<Role> roleList=roleService.list();
        model.addAttribute("roleList",roleList);
        return "forward:/role";
    }

    @RequestMapping("/role/delsR")
    public String delsR(String list,Model model) {
        String[] split = list.split(",");
        List<Integer> ids=new ArrayList<>();
        for (String s : split) {
            ids.add(Integer.parseInt(s));
        }
        roleService.removeByIds(ids);
        model.addAttribute("msg", "删除角色成功");
        List<Role> roleList=roleService.list();
        model.addAttribute("roleList",roleList);
        return "forward:/role";
    }

    @RequestMapping("/role/addManager")
    public String addManager(String username,String roleid,Model model) {
        Manager manager=new Manager(username, Constants.DEFAULT_PASSWORD,Integer.parseInt(roleid));
        manager.insert();
        model.addAttribute("msg","新增管理员成功");
        return "forward:/role";
    }

    @RequestMapping("/role/roleListJson")
    @ResponseBody
    public List<Role> roleListJson() {
        List<Role> roleList=roleService.list();
        return roleList;
    }

}
