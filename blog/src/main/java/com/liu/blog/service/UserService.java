package com.liu.blog.service;

import com.liu.blog.entity.User;
import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2021-07-26 18:17:04
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param  主键
     * @return 实例对象
     */
    User queryById( Integer id);



}