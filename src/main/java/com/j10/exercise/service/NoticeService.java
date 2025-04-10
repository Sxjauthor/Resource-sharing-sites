package com.j10.exercise.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.j10.exercise.bean.Notice;

import java.util.List;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 19:15
 */
public interface NoticeService extends IService<Notice> {
    List<Notice> selectAll();
}
