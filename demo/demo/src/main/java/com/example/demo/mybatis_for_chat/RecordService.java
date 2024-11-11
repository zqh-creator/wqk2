package com.example.demo.mybatis_for_chat;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface RecordService {
    // 保存聊天记录方法
    void saveChatRecord(ChatRecord chatRecord);
    void saveUserRecord(UserRecord userRecord);
    // 根据 ID 获取聊天记录方法
    ChatRecord getChatRecordById(Long id);
    UserRecord getUserRecordById(Long id);
    List<ChatRecord> getChatRecordReceiverId(String ID);
    List<FriendsRecord> getFriendsRecordUserId(String ID);
    List<UserRecord> getUserRecordUserId(String ID);
    // 更新聊天记录方法
    int updateChatRecord(ChatRecord chatRecord);
    int updateUserRecord(UserRecord userRecord);
    // 根据 ID 删除聊天记录方法
    int deleteChatRecordById(Long id);
    ResponseEntity<RecordServiceRealize.UploadProgress> downloadFile(Long id);
}
