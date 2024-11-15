package com.example.demo.mybatis_for_chat.record;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("user_record")
@Data//表名
@AllArgsConstructor
@NoArgsConstructor
public class UserRecord {
    //private Long id;
    private String username;
    private String userId;
    private String password;

}
