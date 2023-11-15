package com.nor3stbackend.www.member.command.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateDto {
    private String username;
    private String password;
    private String department;
}
