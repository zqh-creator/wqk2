package com.example.csms.service;

import com.example.csms.entity.Registration;
import com.example.csms.entity.Teacher;

import java.util.List;

public interface RegistrationService {

    int saveRegistration(Registration registrationChar);

    List<Registration> selectRegistrationByStudentId(String studentId);

    void updateRegistrationById(Registration registration) throws Exception;

    List<Registration> selectPendingRegistrationByStatus(String status);
}