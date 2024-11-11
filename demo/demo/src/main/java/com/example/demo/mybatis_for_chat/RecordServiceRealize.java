package com.example.demo.mybatis_for_chat;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.io.*;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.client.RestTemplate;

@Service
public class RecordServiceRealize implements RecordService {

    private ChatRecordMapper chatRecordMapper;
    private UserRecordMapper userRecordMapper;
    private FriendsRecordMapper friendsRecordMapper;

    @Autowired
    public void setChatRecordServiceRealize(ChatRecordMapper chatRecordMapper) {
        this.chatRecordMapper = chatRecordMapper;
    }

    public void setUserRecordMapper(UserRecordMapper userRecordMapper) {
        this.userRecordMapper = userRecordMapper;
    }

    public void setFriendsRecordMapper(FriendsRecordMapper friendsRecordMapper) {
        this.friendsRecordMapper = friendsRecordMapper;
    }


    @Override
    // 保存聊天记录到数据库
    public void saveChatRecord(ChatRecord chatRecord) {
        chatRecordMapper.insert(chatRecord);
    }

    //保存用户列表
    public void saveUserRecord(UserRecord userRecord) {
        userRecordMapper.insert(userRecord);
    }
    //保存好友列表


    @Override
    // 根据 ID 从数据库中获取聊天记录
    public ChatRecord getChatRecordById(Long id) {
        return chatRecordMapper.selectById(id);
    }

    public UserRecord getUserRecordById(Long id) {
        return userRecordMapper.selectById(id);
    }

    public List<ChatRecord> getChatRecordReceiverId(String ID) {
        QueryWrapper<ChatRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiverId", ID);
        return chatRecordMapper.selectList(queryWrapper);
    }

    //获取符合条件的好友列表
    public List<FriendsRecord> getFriendsRecordUserId(String ID) {
        QueryWrapper<FriendsRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("UserId", ID);
        return friendsRecordMapper.selectList(queryWrapper);
    }

    //获取符合条件的用户列表
    public List<UserRecord> getUserRecordUserId(String ID) {
        QueryWrapper<UserRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("UserId", ID);
        return userRecordMapper.selectList(queryWrapper);
    }

    @Override
    // 更新数据库中的聊天记录
    public int updateChatRecord(ChatRecord chatRecord) {
        return chatRecordMapper.updateById(chatRecord);
    }

    public int updateUserRecord(UserRecord userRecord) {
        return userRecordMapper.updateById(userRecord);
    }

    @Override
    // 根据 ID 删除数据库中的聊天记录
    public int deleteChatRecordById(Long id) {
        return chatRecordMapper.deleteById(id);
    }

    // 添加HTTP头部，给予文件上传地址下载文件

}

    
    
    
