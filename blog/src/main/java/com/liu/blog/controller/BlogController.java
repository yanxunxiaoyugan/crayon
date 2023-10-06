package com.liu.blog.controller;

import com.liu.blog.entity.Blog;
import com.liu.blog.service.BlogService;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Blog)表控制层
 *
 * @author makejava
 * @since 2021-07-27 14:44:52
 */
@RestController
@RequestMapping("blog")
public class BlogController {
    public BlogController(){
        System.out.println("BlogController");
    }
    /**
     * 服务对象
     */
    @Resource
    private BlogService blogService;


}