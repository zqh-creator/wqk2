package com.example.demo.websocketAchieve;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.mybatis_for_chat.*;
import com.example.demo.mybatis_for_chat.filetransfer.FileTransferServer;
import com.example.demo.mybatis_for_chat.mapper.ChatRecordMapper;
import com.example.demo.mybatis_for_chat.mapper.FriendsRecordMapper;
import com.example.demo.mybatis_for_chat.mapper.UserRecordMapper;
import com.example.demo.mybatis_for_chat.record.ChatRecord;
import com.example.demo.mybatis_for_chat.record.FriendsRecord;
import com.example.demo.mybatis_for_chat.record.UserRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

@Component
public class WebSocketHandlerImpl extends BinaryWebSocketHandler {
    // 用于存储客户端连接
    //customIdAndSessionIdList的键值对为  会话id：自定义id
    private static final ConcurrentHashMap<String, String> customIdAndSessionIdList = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, WebSocketSession> sessionsList = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(WebSocketHandlerImpl.class);
    private Long id=0L;
    private final RecordService service;

    @Autowired
    public WebSocketHandlerImpl(RecordService recordService) {
        this.service = recordService;
    }

    ObjectMapper mapper = new ObjectMapper();


    @Override
//WebSocketSession会话接口在服务器同意连接请求的时候就完成自动创建
    // 将新连接的客户端会话添加到sessionList列表中
    public void afterConnectionEstablished(WebSocketSession session) {

        if (session.isOpen()) {
            System.out.println(" 连接成功，等待用户输入登录密码");
        }
    }

    //二进制字符串解析转化为Map
    private Map<String, Object> parseMessage(String payload) {
        // 简单解析客户端发送的消息为Map形式
        HashMap<String, Object> result;
        ObjectMapper mapper = new ObjectMapper();//创造转化器
        try {
            result = mapper.readValue(payload, HashMap.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @MessageMapping("/websocket/chat")
    @Override
    //handleTextMessage 方法用于处理客户端发送的文本消息，根据消息类型（假设为 ice 或 sdp ）分别处理并转发信息。
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {

    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
        log.info(textMessage.getPayload());
        //连接服务器的客户端与服务器的会话id
        String client_ID = session.getId();


        /*初始化两个map表*/
        String payload = textMessage.getPayload();//从message中获取二进制流
        try {
            Map<String, Object> messageMap = parseMessage(payload);//parse为自定义函数
            //只要客户端发起登录， customIdAndSessionIdList和 sessionsList就会更新
            if (customIdAndSessionIdList.get(client_ID) == null) {
                customIdAndSessionIdList.put(client_ID, String.valueOf(messageMap.get("sourceUserId")));
                sessionsList.put(String.valueOf(messageMap.get("sourceUserId")), session);
                System.out.println("两个map表初始化成功");
            }
            /*初始化两个map表（结束）*/


            if ("init".equals(messageMap.get("type"))) {


                //登录检验密码部分
                int checkResult = 0; //用于验证密码是否正确的变量
                //验证密码是否正确
                List<UserRecord> userRecordList = service.getUserRecordUserId(String.valueOf(messageMap.get("sourceUserId")));
                if (userRecordList.isEmpty()) {
                    System.out.println("提起表格失败");
                }
                for (UserRecord userRecord : userRecordList) {
                    if (Objects.equals(userRecord.getPassword(), String.valueOf(messageMap.get("data")))) {
                        checkResult = 1;//密码正确，查结果赋值为1，用户即将登录
                        System.out.println("密码正确");
                        //如果数据库中存在该ID用户的消息，需要将消息发送出去，且发送完删除d对应消除信息
                        if (customIdAndSessionIdList.get(session.getId()) != null) {
                            SendingDeletingDatabaseOfMessages(session, customIdAndSessionIdList.get(session.getId()));
                        }
                    }
                }
                //向前端发送检查结果
                String Message_send;
                try {
                    Message_send = mapper.writeValueAsString(checkResult);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                try {
                    session.sendMessage(new TextMessage(Message_send));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //登录检验密码部分（结束）


                //此处需要根据数据包提供的ID向客户端返回一个好友列表

                List<FriendsRecord> friendsRecordList = service.getFriendsRecordUserId(String.valueOf(messageMap.get("sourceUserId")));
                if (friendsRecordList.isEmpty()) {
                    System.out.println("好友表格提取失败");
                }
                ArrayList<String> friengsArrayList = new ArrayList<>();
                for (FriendsRecord friendsRecord : friendsRecordList) {
                    friengsArrayList.add(friendsRecord.getFriendId());

                }
                String Message_list;
                try {
                    Message_list = mapper.writeValueAsString(friengsArrayList);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                try {
                    session.sendMessage(new TextMessage(Message_list));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("好友列表发送成功");
            }
            //此处需要根据数据包提供的ID向客户端返回一个好友列表（结束）


            else if ("link".equals(messageMap.get("type"))) {
                //addSdpInfo(client_ID, messageMap.get("data"));
                // 将ice,SDP信息转发给目标客户端
                forwardIceAndSdp(String.valueOf(messageMap.get("targetUserId")), messageMap.get("data"));
            } else if ("chat".equals(messageMap.get("type"))) {
                forwardMessage(String.valueOf(messageMap.get("sourceUserId")), String.valueOf(messageMap.get("targetUserId")), messageMap.get("data"));

            }
        } catch (Exception e) {
            System.err.println("Error parsing message: " + e.getMessage());
        }
    }

    //访问数据库，查看是否存有待发给客户端的消息，若有发送给客户端并且删除已经发送的消息
    public void SendingDeletingDatabaseOfMessages(WebSocketSession session1, String ID) {
        List<ChatRecord> chatRecordList = service.getChatRecordReceiverId(ID);
        if (chatRecordList.isEmpty()) {
            System.out.println("该客户端没有待发送的消息");
        } else {
            for (ChatRecord chatRecord : chatRecordList) {
                String Message_leftover;
                try {
                    Message_leftover = mapper.writeValueAsString(chatRecord.getMessageContent());
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                try {
                    session1.sendMessage(new TextMessage(Message_leftover));
                    System.out.println("消息发送成功");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //发送完毕，删除发送的消息
                int i = service.deleteChatRecordById(chatRecord.getId());
                id--;
                if (i > 0) System.out.println("消息删除成功");
            }
        }
    }


    @Mapper
    // forwardIceAndSdp方法用于将收到的ICE和SDP信息转发给其他客户端。
    private void forwardIceAndSdp(String targetId, Object data) throws IOException {

        WebSocketSession targetSession = sessionsList.get(targetId);
        if (targetSession != null) {
            ObjectMapper mapper = new ObjectMapper();
            String Message_ICEAndSDP = mapper.writeValueAsString(data);
            targetSession.sendMessage(new TextMessage(Message_ICEAndSDP));
        } else {
            System.out.println("对方不在线，无法交换ICE和SDP");
        }


    }

    private void forwardMessage(String offId, String targetId, Object data) throws IOException {

        WebSocketSession targetSession = sessionsList.get(targetId);
        if (targetSession != null) {
            ObjectMapper mapper = new ObjectMapper();
            if (targetSession.isOpen())//如果会话始终处于打开状态，发送文本消息
            {
                String Message_4 = mapper.writeValueAsString(data);
                targetSession.sendMessage(new TextMessage(Message_4));
                System.out.println("消息已发出到：" + targetId);

            } else {
                System.out.println("对方的会话处于关闭状态");
            }
        } else //将文本消息存入数据库
        {
            System.out.println("用户：" + targetId + "未连接服务器");
            id++;
            ChatRecord chatRecord = new ChatRecord();
            chatRecord.setId(id);;
            chatRecord.setFilePath(null);
            chatRecord.setMessageContent(String.valueOf(data));
            chatRecord.setReceiverId(targetId);
            chatRecord.setSenderId(offId);
            chatRecord.setMessageType("text");
            service.saveChatRecord(chatRecord);

        }

    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        // 从列表中移除关闭连接的客户端会话
        String clientId = session.getId();
        if (closeStatus.getCode() == 1000) {
            System.out.println(customIdAndSessionIdList.get(clientId) + "与服务器的WebSocket连接正常关闭");
        } else if (closeStatus.getCode() == 1001) {
            System.out.println("端点离开了，可能是页面刷新等原因：" + closeStatus.getReason());
        } else {
            System.out.println("WebSocket连接异常关闭，代码：" + closeStatus.getCode() + "，原因：" + closeStatus.getReason());
        }
        sessionsList.remove(customIdAndSessionIdList.get(clientId));
        customIdAndSessionIdList.remove(clientId);
    }
}


