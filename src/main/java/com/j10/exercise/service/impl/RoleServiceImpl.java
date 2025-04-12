package com.j10.exercise.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j10.exercise.bean.RA;
import com.j10.exercise.bean.Role;
import com.j10.exercise.exception.CustomerException;
import com.j10.exercise.mapper.RoleMapper;
import com.j10.exercise.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/12 15:06
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void addRole(Role role, List<Integer> aids) {
        //1:新增角色到表role
        String reg="^[A-Z][a-zA-Z0-9]{1,4}$";
        if(!role.getRolename().matches(reg)){
            throw new CustomerException("用户名格式:2-5位字母或数字组成,大写字母开头",400);
        }
        if(role.getDisplay()==null||role.getDisplay().length()==0){
            throw new CustomerException("描述不能为空",400);
        }
        roleMapper.myInsert(role); //2:selectKey获取自增的主键
        //2:将rid和aid加入表ra
        for (int i = 0; i < aids.size(); i++) {
            RA ra=new RA();
            ra.setRid(role.getId()).setAid(aids.get(i));
            ra.insert();
        }
    }

    @Override
    public void updateRole(Role role, List<Integer> aids) {
        //1:根据角色id修改角色表role信息(先删再增)
        role.deleteById(); //级联删除ra里原来的关系数据
        role.insert();
        //2:根据角色id新增ra表关系数据
        for (int i = 0; i < aids.size(); i++) {
            RA ra=new RA();
            ra.setRid(role.getId()).setAid(aids.get(i));
            ra.insert();
        }
    }
}
