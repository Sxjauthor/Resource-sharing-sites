package com.j10.exercise.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j10.exercise.bean.Favorite;
import com.j10.exercise.bean.Member;
import com.j10.exercise.bean.Resource;
import com.j10.exercise.mapper.ResourceMapper;
import com.j10.exercise.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 22:27
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService{
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> selectR1() {
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",1).eq("status",1).orderByDesc("joindate");
        List<Resource> resourceList = resourceMapper.selectList(queryWrapper);
        for (Resource resource : resourceList) {
            String supname = resourceMapper.selectSupname(resource.getType());
            String subname = resourceMapper.selectSubname(resource.getSub());
            resource.setSupname(supname);
            resource.setSubname(subname);
        }
        return resourceList;
    }

    @Override
    public List<Resource> selectR2() {
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",2).eq("status",1).orderByDesc("joindate");
        List<Resource> resourceList = resourceMapper.selectList(queryWrapper);
        for (Resource resource : resourceList) {
            String supname = resourceMapper.selectSupname(resource.getType());
            resource.setSupname(supname);
        }
        return resourceList;
    }

    @Override
    public List<Resource> selectR3() {
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",3).eq("status",1).orderByDesc("joindate");
        List<Resource> resourceList = resourceMapper.selectList(queryWrapper);
        for (Resource resource : resourceList) {
            String supname = resourceMapper.selectSupname(resource.getType());
            resource.setSupname(supname);
        }
        return resourceList;
    }

    @Override
    public Resource selectDetail(Resource res) {
        Resource resource = resourceMapper.selectById(res.getId());
        Integer type = resource.getType();
        if(type == 2||type == 3){
            String supname = resourceMapper.selectSupname(type);
            resource.setSupname(supname);
        }else{
            String supname = resourceMapper.selectSupname(type);
            resource.setSupname(supname);
            String subname = resourceMapper.selectSubname(resource.getSub());
            resource.setSubname(subname);
        }
        return resource;
    }

    @Override
    public void updateFav(Member member, Resource res, int i1) {
        //更新收藏量
        UpdateWrapper<Resource> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id", res.getId()).set("fav", res.getFav()+i1);
        resourceMapper.update(updateWrapper);
        //修改收藏表
        Favorite favorite=new Favorite();
        if(i1==1){
            favorite.setMid(member.getId()).setRid(res.getId());
            favorite.insert();
        }else if(i1==-1){
            QueryWrapper<Favorite> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("mid", member.getId()).eq("rid", res.getId());
            favorite.delete(queryWrapper);
        }
    }
}
