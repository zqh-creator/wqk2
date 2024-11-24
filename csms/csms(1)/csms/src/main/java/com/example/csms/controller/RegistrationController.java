package com.example.csms.controller;
import com.example.csms.entity.*;
import com.example.csms.service.MatchService;
import com.example.csms.service.RegistrationService;
import com.example.csms.service.StudentService;
import com.example.csms.service.TeacherService;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final MatchService matchService;

    public RegistrationController(RegistrationService registrationService, StudentService studentService, TeacherService teacherService, MatchService matchService) {
        this.registrationService = registrationService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.matchService = matchService;
    }

    /*报名操作开始*/
    //报名竞赛，初始为未审核PENDING
    @PostMapping("/Registration/saveRegistration")
    public Result saveRegistration(Registration registration) {
        registration.setStatus("PENDING");
        int num = registrationService.saveRegistration(registration);
        if (num > 0) {
            return Result.success();
        } else {
            return Result.error("报名失败");
        }
    }

    //获取报名表（管理员审核）
    @GetMapping("/registration/getAllRegistration")
    public Result getAllRegistration(Registration registration) {
        List<registrationDTO> list=new ArrayList<>();
        if (registrationService.selectPendingRegistrationByStatus(registration.getStatus()) != null) {
            for (Registration reg : registrationService.selectPendingRegistrationByStatus(registration.getStatus()))
            {
                   Student stu=new Student();
                   Teacher teacher=new Teacher();
                   Match match=new Match();
                   stu=studentService.selectStudentByStudentId(reg.getStudentId());
                   teacher=teacherService.selectTeacherById(reg.getTeacherId());
                   match=matchService.selectByMatchId(reg.getMatchId());
                   registrationDTO dto=new registrationDTO(reg,stu.getUsername(),stu.getPhone(),teacher.getUsername(),teacher.getPhone(),match.getName());
                   list.add(dto);
            }
            return Result.success("管理员获得报名表成功",list);
        } else {
            return Result.error("不存在待审核的报名表");
        }
    }

    //获取报名表
    @GetMapping("/registration/student/{userId}")
    public Result getAllRegistration(@PathVariable String userId) {
        //提供一个方法，参数为studentId获得对应记录，返回值可能存在多个,返回registration记录集合 OK
        if (registrationService.selectRegistrationByStudentId(userId) != null) {
            return  Result.success(registrationService.selectRegistrationByStudentId(userId));
        }
        else
        {
            return Result.error("获取失败，该用户不存在报名表");
        }

    }

    //修改报名表
    @PutMapping("/Registration/updateRegistration")
    public Result updateRegistration(Registration registration) {
        //提供一个更新报名表的方法，该方法以编号id为参数,将参数registration记录覆盖旧记录 ok
        try {
            registrationService.updateRegistrationById(registration);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.success("更新成功",registration.getId());
    }
}