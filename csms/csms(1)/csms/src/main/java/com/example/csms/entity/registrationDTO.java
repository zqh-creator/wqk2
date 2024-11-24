package com.example.csms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class registrationDTO {
    private Registration registration;
    private String studentName;
    private String studentPhone;
    private String teacherName;
    private String teacherPhone;
    private String matchName;
}
