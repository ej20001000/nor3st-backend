package com.nor3stbackend.www.member.command.application.dto;

import lombok.Data;

@Data
public class EmployeeUpdateDto {
    private String username;
    private String password;
    private String department;
}
