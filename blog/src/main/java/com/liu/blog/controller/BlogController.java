package com.liu.blog.controller;

import com.liu.blog.entity.Blog;
import com.liu.blog.service.BlogService;
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
    /**
     * 服务对象
     */
    @Resource
    private BlogService blogService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Blog selectOne(@RequestParam("id") Integer id) {
        return this.blogService.queryById(id);
    }

}