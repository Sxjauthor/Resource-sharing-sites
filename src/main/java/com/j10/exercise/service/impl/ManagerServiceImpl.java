package com.j10.exercise.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j10.exercise.bean.Action;
import com.j10.exercise.bean.Manager;
import com.j10.exercise.bean.Role;
import com.j10.exercise.exception.CustomerException;
import com.j10.exercise.mapper.ActionMapper;
import com.j10.exercise.mapper.ManagerMapper;
import com.j10.exercise.service.ManagerService;
import com.j10.exercise.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 17:00
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private ActionMapper actionMapper;

    @Override
    public Manager login(Manager m) {
        //1:根据用户名和密码查询管理员基本信息
        //2:根据角色id查询action信息
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", m.getUsername());
        Manager manager=managerMapper.selectOne(queryWrapper);
        if(manager==null){
            return null;
        }else{
            if(m.getPassword().equals(manager.getPassword())){
                List<Action> actionList = actionMapper.selectAction(manager.getRoleid());
                manager.setActionList(actionList);
                Role role=new Role();
                role = role.selectById(manager.getRoleid());
                manager.setRolename(role.getRolename());
                manager.setDisplay(role.getDisplay());
                return manager;
            }else{
                throw new CustomerException(Constants.PASSWORD_MSG,400);
            }
        }
    }

}
