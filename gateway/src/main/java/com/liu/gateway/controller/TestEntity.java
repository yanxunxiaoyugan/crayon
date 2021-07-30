package com.liu.gateway.controller;


import java.io.Serializable;

public class TestEntity implements Serializable {
    private Integer id;
    private String name;

    public TestEntity(){

    }
    public TestEntity(Integer id,String name){
        this.id = id;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
