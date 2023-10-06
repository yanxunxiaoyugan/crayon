package com.liu.blog.entity;

import org.hibernate.annotations.Entity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2021-07-26 18:17:04
 */
@Entity
@javax.persistence.Entity(name = "user")
@Table(appliesTo = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -18595700514491988L;
    @Id
    private Integer id;
    
    private String username;
    
    private String password;
    private Integer status;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    private String email;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    public LocalDateTime getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(LocalDateTime update_time) {
        this.update_time = update_time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}