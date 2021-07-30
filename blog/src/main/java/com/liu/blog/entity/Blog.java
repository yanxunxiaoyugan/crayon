package com.liu.blog.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * (Blog)实体类
 *
 * @author makejava
 * @since 2021-07-27 14:44:49
 */
public class Blog implements Serializable {
    private static final long serialVersionUID = 660475391278556614L;
    
    private Integer id;
    
    private Integer userId;
    
    private String title;
    
    private String content;
    
    private Integer status;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    private String tag;
    
    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}