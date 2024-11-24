package com.example.csms.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonSerialize
@JsonDeserialize
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code ;
    private String msg ;
    private Object data ;
    public static Result success(Object data){
        return new Result(1,"success",data) ;
    }
    public static Result success(){
        return new Result(1,"success",null);
    }
    public static Result success(String message){return new Result(1,message,null);}
    public static Result success(String message,Object data){
        return new Result(1,message,data);
    }
    public static Result error(String msg){
        return new Result(0,msg,null) ;
    }
}