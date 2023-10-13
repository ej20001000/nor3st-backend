package com.nor3stbackend.www.member.command.application.dto;

import lombok.Data;

@Data
public class EmployeeRegistrationDto {
    private Long memberId;
    private String password;
    private String username;
    private String employeeNo;
    private String companyName;
    private String role;
    private String rank;
    private String department;
}
