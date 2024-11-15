package com.example.demo.mybatis_for_chat;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mybatis_for_chat.mapper.ChatRecordMapper;
import com.example.demo.mybatis_for_chat.mapper.FriendsRecordMapper;
import com.example.demo.mybatis_for_chat.mapper.UserRecordMapper;
import com.example.demo.mybatis_for_chat.record.ChatRecord;
import com.example.demo.mybatis_for_chat.record.FriendsRecord;
import com.example.demo.mybatis_for_chat.record.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RecordServiceRealize implements RecordService {

    private final ChatRecordMapper chatRecordMapper;
    private final UserRecordMapper userRecordMapper;
    private final FriendsRecordMapper friendsRecordMapper;

    public RecordServiceRealize(ChatRecordMapper chatRecordMapper, UserRecordMapper userRecordMapper, FriendsRecordMapper friendsRecordMapper) {
        this.chatRecordMapper = chatRecordMapper;
        this.userRecordMapper = userRecordMapper;
        this.friendsRecordMapper = friendsRecordMapper;
    }

    @Override
    // 保存聊天记录到数据库
    public void saveChatRecord(ChatRecord chatRecord) {
        chatRecordMapper.insert(chatRecord);
    }

    //保存用户列表
    @Override
    public void saveUserRecord(UserRecord userRecord) {
        userRecordMapper.insert(userRecord);
    }



    @Override
    // 根据 ID 从数据库中获取聊天记录
    public ChatRecord getChatRecordById(Long id) {
        return chatRecordMapper.selectById(id);
    }

    @Override
    public UserRecord getUserRecordById(Long id) {
        return userRecordMapper.selectById(id);
    }

    @Override
    public List<ChatRecord> getChatRecordReceiverId(String ID) {
        QueryWrapper<ChatRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver_id", ID);
        return chatRecordMapper.selectList(queryWrapper);
    }

    //获取符合条件的好友列表
    @Override
    public List<FriendsRecord> getFriendsRecordUserId(String ID) {
        QueryWrapper<FriendsRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", ID);
        return friendsRecordMapper.selectList(queryWrapper);
    }

    //获取符合条件的用户列表
    @Override
    public List<UserRecord> getUserRecordUserId(String ID) {
        QueryWrapper<UserRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", ID);
        return userRecordMapper.selectList(queryWrapper);
    }

    @Override
    // 更新数据库中的聊天记录
    public int updateChatRecord(ChatRecord chatRecord) {
        return chatRecordMapper.updateById(chatRecord);
    }

    @Override
    public int updateUserRecord(UserRecord userRecord) {
        return userRecordMapper.updateById(userRecord);
    }

    @Override
    // 根据 ID 删除数据库中的聊天记录
    public int deleteChatRecordById(Long id) {
        return chatRecordMapper.deleteById(id);
    }


}

    
    
    
