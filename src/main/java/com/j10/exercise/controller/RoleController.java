package com.j10.exercise.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.j10.exercise.bean.Action;
import com.j10.exercise.bean.Manager;
import com.j10.exercise.bean.Role;
import com.j10.exercise.service.ActionService;
import com.j10.exercise.service.RoleService;
import com.j10.exercise.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
        return "admin/role";
    }

    @RequestMapping("/role/roleAddPre")
    public String roleAddPre(Model model) {
        Action action=new Action();
        List<Action> actions=action.selectAll();
        model.addAttribute("actions",actions);
        return "admin/roleadd";
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
        return "admin/roleActionInfo";
    }

    @PutMapping("/role/updateRole")
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
        return "admin/role";
    }

    @PostMapping("/role/updateRole")
    public String updateRole(String roleid, String rolename, String display, String[] actions, Model model) {
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
        return "admin/roleActionInfo";
    }

    @RequestMapping("/role/delRole")
    public String delRole(String id,Model model) {
        Role role=new Role();
        role.setId(Integer.parseInt(id));
        role.deleteById();
        model.addAttribute("msg","删除角色成功");
        List<Role> roleList=roleService.list();
        model.addAttribute("roleList",roleList);
        return "admin/role";
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
        return "admin/role";
    }

    @RequestMapping("/role/roleListJson")
    @ResponseBody
    public List<Role> roleListJson() {
        List<Role> roleList=roleService.list();
        return roleList;
    }

}
