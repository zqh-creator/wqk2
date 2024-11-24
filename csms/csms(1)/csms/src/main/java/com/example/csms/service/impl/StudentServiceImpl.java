package com.example.csms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.csms.entity.Student;
import com.example.csms.entity.Teacher;
import com.example.csms.mapper.StudentMapper;
import com.example.csms.service.StudentService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    //保存学生信息
    @Override
    public int saveStudentInfo(Student student) {
        return studentMapper.insert(student);
    }

    //根据学生userid更新其信息
    @Override
    public void updateStudentById(Student student)throws Exception{
        // 根据传入的student对象中的userId构建查询条件
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", student.getUserId());

        // 先查询该ID对应的记录是否存在
        Student oldStudent = studentMapper.selectOne(queryWrapper);
        if (oldStudent == null) {
            throw new Exception("要更新的学生记录不存在，userId: " + student.getUserId());
        }
        else{
            // 如果存在，则执行更新操作(同理只会取参数中的主码来使用)
            studentMapper.updateById(student);
        }

    }

    //根据学生用户名获取其个人信息
    @Override
    public Student selectStudentByStudentId (String userId){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return studentMapper.selectOne(queryWrapper);
    }

}
