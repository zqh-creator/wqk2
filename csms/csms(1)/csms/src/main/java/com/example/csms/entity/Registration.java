package com.example.csms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("registration_char")
public class Registration {
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;
    private String studentId;
    private String matchId;
    private String teacherId;
    private String status;
}