package com.example.demo;

import com.baomidou.mybatisplus.annotation.TableId;

public class User {
    @TableId
    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }
}
