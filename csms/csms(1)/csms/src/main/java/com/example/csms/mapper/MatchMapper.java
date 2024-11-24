package com.example.csms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.csms.entity.Match;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MatchMapper extends BaseMapper<Match> {

}