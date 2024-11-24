package com.example.csms.service;

import com.example.csms.entity.Student;

public interface StudentService {

    int saveStudentInfo(Student student);

    void updateStudentById(Student student) throws Exception;

    Student selectStudentByStudentId (String userId);
}
