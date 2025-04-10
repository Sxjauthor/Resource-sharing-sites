package com.j10.exercise.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j10.exercise.bean.Notice;
import com.j10.exercise.mapper.NoticeMapper;
import com.j10.exercise.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 19:16
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> selectAll() {
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notice::getStatus, 2).orderByDesc(Notice::getPublishtime);
        Page<Notice> page = new Page<>(1, 5);
        Page<Notice> notices = noticeMapper.selectPage(page,queryWrapper);
        List<Notice> records = notices.getRecords();
        for (Notice notice : records) {
            String day = notice.getPublishtime().substring(8, 10);
            String year_month = notice.getPublishtime().substring(0, 7);
            notice.setDay(day);
            notice.setYm(year_month);
        }
        return records;
    }
}
