package com.j10.exercise.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.j10.exercise.bean.Member;
import com.j10.exercise.bean.Resource;

import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 22:26
 */
public interface ResourceService extends IService<Resource> {

    List<Resource> selectR1();
    List<Resource> selectR2();
    List<Resource> selectR3();

    /**
     * 根据id查详情,包括大小分类
     * @param res
     * @return
     */
    Resource selectDetail(Resource res);

    void updateFav(Member member, Resource res, int i1);

    List<Resource> search(Resource r, String search);

    boolean share(Resource r);
}
