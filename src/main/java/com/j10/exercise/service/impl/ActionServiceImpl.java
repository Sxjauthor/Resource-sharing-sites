package com.j10.exercise.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j10.exercise.bean.Action;
import com.j10.exercise.mapper.ActionMapper;
import com.j10.exercise.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/12 16:43
 */
@Service
public class ActionServiceImpl extends ServiceImpl<ActionMapper, Action> implements ActionService {
    @Autowired
    private ActionMapper actionMapper;

    @Override
    public List<Action> selectAction(Integer rid) {
        //1:查询所有的action
        //2:查询角色id在ra表里对应的aid
        //3:给1中的action设置have属性
        List<Action> allActions = actionMapper.selectList(null);
        List<Action> actionList = actionMapper.selectAction(rid);
        for (Action action : allActions) {
            if(actionList.contains(action)){
                action.setHave("1");
            }
        }
        return allActions;
    }
}
