package com.example.csms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.csms.entity.Teacher;
import com.example.csms.mapper.TeacherMapper;
import com.example.csms.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public List<Teacher> selectTeacherByTeacherName (String userName){
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userName);
        return teacherMapper.selectList(queryWrapper);
    }

    //存入老师信息
    @Override
    public int saveTeacher(Teacher teacher){
        return teacherMapper.insert(teacher);
    }

    //根据老师userid更新信息
    @Override
    public void updateTeacherById(Teacher teacher) throws Exception {
        // 根据传入的teacher对象中的userId构建查询条件
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", teacher.getUserId());

        // 先查询该ID对应的记录是否存在
        Teacher oldTeacher = teacherMapper.selectOne(queryWrapper);
        if (oldTeacher == null) {
            throw new Exception("要更新的教师记录不存在，userId: " + teacher.getUserId());
        }
        else
        {
            // 如果存在，则执行更新操作(这里存入teacher会自动提取它的主码)
            teacherMapper.updateById(teacher);
        }
    }

    @Override
    public  Teacher selectTeacherById(String teacherId){
          QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
          queryWrapper.eq("user_id", teacherId);
          return teacherMapper.selectOne(queryWrapper);
    }

}