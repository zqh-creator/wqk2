package com.example.demo.mybatis_for_chat.record;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("user_record")
public class UserRecord {
    private Long id;
    private String UserName;
    private String UserId;
    private String Password;

    public Long getId(){return id;}

    public void setUserName(String userName){this.UserName=userName;}

    public String getUserName(){return UserName;}

    public void setUserId(String userId){this.UserId=userId;}

    public String getUserId(){return UserId;}

    public void setPassword(String password) {Password = password;}

    public String getPassword(){return Password;}
}
