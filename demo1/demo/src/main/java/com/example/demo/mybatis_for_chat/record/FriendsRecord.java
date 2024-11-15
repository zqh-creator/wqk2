package com.example.demo.mybatis_for_chat.record;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("friends_record")
@Data//表名
@AllArgsConstructor
@NoArgsConstructor
public class FriendsRecord {
         private Long id;
         private String userId;
         private String friendId;

}
