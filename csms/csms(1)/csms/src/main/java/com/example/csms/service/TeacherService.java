package com.example.csms.service;

import com.example.csms.entity.Teacher;

import java.util.List;


public interface TeacherService {

    List<Teacher> selectTeacherByTeacherName(String userName);

    Teacher selectTeacherById(String teacherId);

    int saveTeacher(Teacher teacher);

    void updateTeacherById(Teacher teacher) throws Exception;
}
