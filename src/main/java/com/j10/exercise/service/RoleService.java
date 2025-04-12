package com.j10.exercise.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.j10.exercise.bean.Role;

import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/12 15:06
 */
public interface RoleService extends IService<Role> {
    void addRole(Role role, List<Integer> aids);

    void updateRole(Role role, List<Integer> aids);
}
