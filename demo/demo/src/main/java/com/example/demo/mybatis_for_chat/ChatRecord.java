package com.example.demo.mybatis_for_chat;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("chat_record")   //表名
public class ChatRecord {
    private Long id;
    private String senderId;
    private String receiverId;
    private String messageType;
    private String messageContent;
    private java.util.Date sendTime;
    private String filePath;

    public Long getId() {
        return id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverI(String receiverId) {
        this.receiverId = receiverId;
    }
    public String getMessageType() {return messageType;}

    public void setMessageType(String messageType) {this.messageType = messageType;}

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}