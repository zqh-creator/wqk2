package com.example.demo.mybatis_for_chat.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.mybatis_for_chat.record.UserRecord;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserRecordMapper extends BaseMapper<UserRecord> {
}
