package com.example.demo.mybatis_for_chat.record;
import com.baomidou.mybatisplus.annotation.TableName;
@TableName("friends_record")
public class FriendsRecord {
         private Long id;
         private String UserId;
         private String FriendsId;

         public Long getId(){return id;}

        public void setFriendsId(String friendsId) {FriendsId = friendsId;}

        public String getFriendsId(){return FriendsId;}

        public void setUserId(String userId) {UserId = userId;}

        public String getUserId(){return UserId;}
}
