package com.example.demo.mybatis_for_chat.record;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("chat_record")
@Data//表名
@AllArgsConstructor
@NoArgsConstructor
public class ChatRecord {
    private Long id;
    private String senderId;
    private String receiverId;
    private String messageType;
    private String messageContent;
    private java.util.Date sendTime;
    private String filePath;


}