package com.example.csms.controller;
import com.example.csms.entity.*;
import com.example.csms.service.LoginService;
import com.example.csms.service.StudentService;
import com.example.csms.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
public class UserController
{
    private final LoginService loginService;
    public  final TeacherService teacherService;
    public final StudentService studentService;
    public UserController(LoginService loginService, TeacherService teacherService,StudentService studentService)
    {
        this.loginService = loginService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    //登录操作
    @PostMapping(value ="/user/login" )
    public Result loginCheck( Login login)
    {
       String password = login.getPassword();
        String userId = login.getUserId();
        Login login1=loginService.getLoginByUserId(userId);
       if (login1.getPassword().equals(password)) {
         return Result.success("登录成功", login1);

        } else {
         return Result.error("登录失败");
        }
    }
    //登录操作


    //注册操作
    @PostMapping(value = "/user/register" )
    public Result saveLogin(Login login)
    {
        if(loginService.registerUser(login))
        {
          if(login.getRole().equals("teacher")){
              Teacher teacher=new Teacher();
              teacher.setUserId(login.getUserId());
              teacherService.saveTeacher(teacher);
          }
          if(login.getRole().equals("student")){
              Student student=new Student();
              student.setUserId(login.getUserId());
              studentService.saveStudentInfo(student);
          }
            return Result.success("注册成功",login);
        }
        else
        {
           return Result.error("注册失败");
        }

    }
    //注册操作

    /*老师个人信息表操做开始*/
    //修改老师个人信息表
    @PutMapping("/user/updateTeacher")
    public Result updateTeacher(Teacher teacher) {
        //需要一个跟更新teacher表的方法,参数为teacherId，查询数据库是否存在ID，如果不存在抛出异常，存在，用参数teacher更新原有的记录   ok
        try {
            teacherService.updateTeacherById(teacher);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.success();
    }
    //根据老师姓名获取个人信息表
    @GetMapping("/user/getTeacher/{teacherName}")
    public Result getTeacher(@PathVariable String teacherName) {
        //需要修改原有的获得teacher表的方法，参数为teacherName，返回一个teacher记录 ok
        List<Teacher> list=teacherService.selectTeacherByTeacherName(teacherName);
        if(!list.isEmpty()){
            return Result.success(list.getFirst());
        }
        else
        {
            return Result.error("不存在该老师，获取失败");
        }
    }
    /*老师个人信息表操作结束*/


    /*学生个人信息表操作开始*/
    //修改学生信息表
    @PutMapping("/user/updateStudent")
    public Result updateStudent(Student student)
    {
        //需要一个跟更新student表的方法,参数为studentId，查询数据库是否存在ID，如果不存在抛出异常，存在，用参数student更新原有的记录 ok
        try {
            studentService.updateStudentById(student);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.success();
    }
    @GetMapping("/user/getStudent/{StudentId}")
    public Result getStudent(@PathVariable String StudentId) {
        //获得student表的方法，参数为studentName，返回一个student记录 ok
        Student student=new Student();
        student=studentService.selectStudentByStudentId(StudentId);
        if(student!=null){
            return Result.success(student);
        }
        else{
            return Result.error("该学生ID不存在，获取失败");
        }
    }
    /*学生个人信息表操作结束*/
}
