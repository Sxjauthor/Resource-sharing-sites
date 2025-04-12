package com.j10.exercise.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.j10.exercise.bean.Action;

import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/12 16:43
 */
public interface ActionService extends IService<Action> {
    List<Action> selectAction(Integer rid);
}
