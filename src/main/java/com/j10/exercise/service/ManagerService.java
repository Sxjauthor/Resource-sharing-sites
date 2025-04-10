package com.j10.exercise.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.j10.exercise.bean.Manager;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 16:59
 */
public interface ManagerService extends IService<Manager> {
    Manager login(Manager manager);
}
