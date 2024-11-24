package com.example.csms.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("student_char")
public class Student {
    @TableId("user_id")
    private String userId;
    private String username;
    private int  age;
    private String gender;
    private String identityNumber;
    private String grade;
    private String major;
    private String studentClass;
    private String department;
    private String profile;
    private String phone;
}
