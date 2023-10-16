package com.nor3stbackend.www.member.command.application.dto;

import lombok.Data;

@Data
public class EmployeeRegistrationDto {
    private String password;
    private String username;
    private String employeeNo;
    private Long companyId;
    private String companyPosition;
    private String department;
}