package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/user/{userId}")
    //获取数据表通过ID
    public User getUser(@PathVariable String userId){
        return userMapper.selectById(userId);
    }
}
