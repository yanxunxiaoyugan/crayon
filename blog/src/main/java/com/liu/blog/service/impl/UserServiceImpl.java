package com.liu.blog.service.impl;

import com.liu.blog.entity.User;
import com.liu.blog.dao.UserDao;
import com.liu.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2021-07-26 18:17:04
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param  主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id ) {
        long start = System.currentTimeMillis();
        User user = this.userDao.queryById(id);
        log.info("service请求耗时：{}",System.currentTimeMillis() - start);
        return user;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param  主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById( Integer id) {
        return this.userDao.deleteById(id) > 0;
    }
}