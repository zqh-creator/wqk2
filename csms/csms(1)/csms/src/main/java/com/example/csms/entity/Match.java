package com.example.csms.entity;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("match_char")
public class Match {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    private String type;
    private String name;
    private String place;
    private String competitionStartTime;
    private String competitionEndTime;
    private String registrationStartTime;
    private String registrationEndTime;
    private String description;
}