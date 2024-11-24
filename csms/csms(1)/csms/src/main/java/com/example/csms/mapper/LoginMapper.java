package com.example.csms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.csms.entity.Login;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoginMapper extends BaseMapper<Login> {

}