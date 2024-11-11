package com.example.demo;
import com.example.demo.mybatis_for_chat.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.*;


public class WebSocketHandlerImpl extends TextWebSocketHandler {
    // 用于存储客户端连接
//customIdAndSessionIdList的键值对为  会话id：自定义id
    private static final ConcurrentHashMap<String, String> customIdAndSessionIdList= new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, WebSocketSession> sessionsList= new ConcurrentHashMap<>();
   /*private final HashMap<String, Map<String, Object>> iceAndsdp_Info = new HashMap<>();*/
    RecordServiceRealize service = new RecordServiceRealize();
    @Override
//WebSocketSession会话接口在服务器同意连接请求的时候就完成自动创建
    // 将新连接的客户端会话添加到sessionList列表中
    public void afterConnectionEstablished(WebSocketSession session) {

        if(session.isOpen()){
            System.out.println( " 连接成功，等待用户输入登录密码");
        }
    }

    //JSON字符串解析转化为Map
    private Map<String, Object> parseMessage(String payload) {
        // 简单解析客户端发送的消息为Map形式
        HashMap<String, Object> result ;
        ObjectMapper mapper = new ObjectMapper();//创造转化器
        try {
            result = mapper.readValue(payload, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    //handleTextMessage 方法用于处理客户端发送的文本消息，根据消息类型（假设为 ice 或 sdp ）分别处理并转发信息。
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        //连接服务器的客户端与服务器的会话id
        String client_ID = session.getId();
        String payload = message.getPayload();//从message中获取JSON字符串
        try {
            Map<String, Object> messageMap = parseMessage(payload);
            //只要客户端发起登录， customIdAndSessionIdList和 sessionsList就会更新
            if(customIdAndSessionIdList.get(client_ID)==null)
            {
                customIdAndSessionIdList.put(client_ID,String.valueOf(messageMap.get("sourceUserId")));
                sessionsList.put(String.valueOf(messageMap.get("sourceUserId")),session);
            }

            if ("init".equals(messageMap.get("type"))) {
                boolean checkResult=false; //用于验证密码是否正确的变量
                ObjectMapper mapper=new ObjectMapper();
                //验证密码是否正确
                List<UserRecord> userRecordList=service.getUserRecordUserId(String.valueOf(messageMap.get("targetUserId")));
                for(UserRecord userRecord:userRecordList)
                {
                    if(Objects.equals(userRecord.getPassword(), String.valueOf(messageMap.get("data"))))
                    {
                        checkResult=true;
                        //如果数据库中存在该ID用户的消息，需要将消息发送出去，且发送完删除d对应消除信息
                        if(customIdAndSessionIdList.get(session.getId())!=null)
                        {
                            List<ChatRecord> chatRecordList=service.getChatRecordReceiverId(customIdAndSessionIdList.get(session.getId()));//此处的client_ID错误！！
                            for(ChatRecord chatRecord:chatRecordList)
                            {
                                String jsonMessage ;
                                try {
                                    jsonMessage = mapper.writeValueAsString(chatRecord.getMessageContent());
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                                try {
                                    session.sendMessage(new TextMessage(jsonMessage));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                int i=service.deleteChatRecordById(chatRecord.getId());
                                if(i>0) System.out.println("消息删除成功");
                            }}
                    }  //密码正确将检查结果赋值为true
                }
                //向前端发送检查结果
                String jsonMessage ;
                try {
                    jsonMessage= mapper.writeValueAsString(checkResult);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                try {
                    session.sendMessage(new TextMessage(jsonMessage));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //此处需要根据数据包提供的ID向客户端返回一个好友列表
                List<FriendsRecord> friendsRecordList=service.getFriendsRecordUserId(String.valueOf(messageMap.get("sourceUserId")));
                ArrayList<String> friengsArrayList = new ArrayList<>();
                for(FriendsRecord friendsRecord:friendsRecordList)
                {
                    friengsArrayList.add(friendsRecord.getFriendsId());

                }
                String jsonMessage1 ;
                try {
                    jsonMessage1 = mapper.writeValueAsString(friengsArrayList);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                try {
                    session.sendMessage(new TextMessage(jsonMessage1));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }



            }
            else if ("link".equals(messageMap.get("type"))) {
                //addSdpInfo(client_ID, messageMap.get("data"));
                // 将ice,SDP信息转发给目标客户端
                    forwardIceAndSdp(String.valueOf(messageMap.get("targetUserId")),messageMap.get("data") );
            }
            else if("chat".equals(messageMap.get("type"))) {
                forwardMessage(String.valueOf(messageMap.get("sourceUserId")),String.valueOf(messageMap.get("targetUserId")),messageMap.get("data") );

            }
            else if("file".equals(messageMap.get("type"))){}
        } catch (Exception e) {
            System.err.println("Error parsing message: " + e.getMessage());
        }
    }

    //更新iceAndSdp_Info列表
    /*public void addIceInfo(String clientId, Object iceData) {
        Map<String, Object> clientInfo = iceAndsdp_Info.get(clientId);
        if (clientInfo == null) {
            clientInfo = new HashMap<>();
        }
        clientInfo.put("ice", iceData);
        iceAndsdp_Info.put(clientId, clientInfo);

    }*/

    /*private void addSdpInfo(String clientId, Object sdpData) {
        Map<String, Object> clientInfo = iceAndsdp_Info.get(clientId);
        if (clientInfo == null) {
            clientInfo = new HashMap<>();
        }
        clientInfo.put("sdp", sdpData);
        iceAndsdp_Info.put(clientId, clientInfo);

    }*/
    @Mapper
    // forwardIceAndSdp方法用于将收到的ICE和SDP信息转发给其他客户端。
    private void forwardIceAndSdp(String targetId, Object data) throws IOException
    {

                WebSocketSession targetSession = sessionsList.get(targetId);
                if(targetSession!=null)
                {
                    ObjectMapper mapper=new ObjectMapper();
                    String jsonMessage = mapper.writeValueAsString(data);
                    targetSession.sendMessage(new TextMessage(jsonMessage));
                }
                else
                {
                    System.out.println("对方不在线，无法交换ICE和SDP");
                }

    }

    private void forwardMessage(String offId,String targetId, Object data)throws IOException
    {
        WebSocketSession targetSession = sessionsList.get(targetId);
        if(targetSession!=null)
        {
            ObjectMapper mapper=new ObjectMapper();
            if(targetSession.isOpen())//如果会话始终处于打开状态，发送文本消息
            {
                String jsonMessage = mapper.writeValueAsString(data);
                targetSession.sendMessage(new TextMessage(jsonMessage));
            }
            else {
                System.out.println("对方的会话处于关闭状态");
            }
        }
        else //将文本消息存入数据库
        {
            ChatRecord chatRecord=new ChatRecord();
            chatRecord.setFilePath(null);
            chatRecord.setMessageContent(String.valueOf(data));
            chatRecord.setReceiverI(targetId);
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
            System.out.println(customIdAndSessionIdList.get(clientId)+"与服务器的WebSocket连接正常关闭");
        } else if (closeStatus.getCode() == 1001) {
            System.out.println("端点离开了，可能是页面刷新等原因：" + closeStatus.getReason());
        } else {
            System.out.println("WebSocket连接异常关闭，代码：" + closeStatus.getCode() + "，原因：" + closeStatus.getReason());
        }
        sessionsList.remove(customIdAndSessionIdList.get(clientId));
        customIdAndSessionIdList.remove(clientId);
    }
}


