package com.example.csms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.csms.entity.Registration;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegistrationMapper extends BaseMapper<Registration> {

    // 保存报名表的方法
    int insert(Registration registrationChar);
}