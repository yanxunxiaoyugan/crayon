package com.liu.blog.service.impl;

import com.liu.blog.dao.BlogRepository;
import com.liu.blog.entity.Blog;

import com.liu.blog.service.BlogService;
import com.liu.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Blog)表服务实现类
 *
 * @author makejava
 * @since 2021-07-27 14:44:51
 */
@Service("blogService")
@Transactional(rollbackFor = Exception.class)
public class BlogServiceImpl implements BlogService {
    @Resource
    private BlogRepository blogDao;
    @Autowired
    private UserService userService;


}