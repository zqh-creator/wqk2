package com.example.csms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.csms.entity.Registration;
import com.example.csms.entity.Teacher;
import com.example.csms.mapper.RegistrationMapper;
import com.example.csms.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationMapper registrationMapper;

    @Override
    public int saveRegistration(Registration registrationChar) {
        return registrationMapper.insert(registrationChar);
    }

    //根据学生id获取属于它的所有报名信息
    @Override
    public List<Registration> selectRegistrationByStudentId(String studentId){
        QueryWrapper<Registration> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", studentId);
        return registrationMapper.selectList(queryWrapper);
    }

    @Override
    public void updateRegistrationById(Registration registration) throws Exception {

        QueryWrapper<Registration> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", registration.getId());

        // 先查询该ID对应的记录是否存在
        Registration oldregistration = registrationMapper.selectOne(queryWrapper);
        if (oldregistration == null) {
            throw new Exception("要更新的记录不存在 ");
        }
        else
        {
            // 如果存在，则执行更新操作(这里存入registration会自动提取它的主码进行传参)
            registrationMapper.updateById(registration);
        }
    }

    //返回未审核的报名表，记得传入参数是status=你要查询对应状态的报名表
    @Override
    public List<Registration> selectPendingRegistrationByStatus(String status){
        QueryWrapper<Registration> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status);
        return registrationMapper.selectList(queryWrapper);
    }
}