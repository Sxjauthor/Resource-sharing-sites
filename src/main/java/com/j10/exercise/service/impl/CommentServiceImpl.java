package com.j10.exercise.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j10.exercise.bean.Comment;
import com.j10.exercise.mapper.CommentMapper;
import com.j10.exercise.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/11 9:22
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}
