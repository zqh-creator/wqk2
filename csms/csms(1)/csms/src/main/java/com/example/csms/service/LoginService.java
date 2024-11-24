package com.example.csms.service;

import com.example.csms.entity.Login;

import java.util.List;

public interface LoginService {

    Login getLoginByUserId(String userId);

    boolean registerUser(Login login);
}