package com.example.csms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("teacher_char")
public class Teacher {
    @TableId("user_id")
    private String userId;
    private String username;
    private String phone;
    private String gender;
    private String teachAge;
    private String major;
    private String department;
    private String profile;
    private int age;
}