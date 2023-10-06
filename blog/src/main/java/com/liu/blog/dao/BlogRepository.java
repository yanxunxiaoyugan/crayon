package com.liu.blog.dao;

import com.liu.blog.entity.Blog;
import com.liu.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Integer> {
}
