package com.nor3stbackend.www.member.command.application.dto;

import lombok.Data;

@Data
public class ManagerRegistrationDto {
    private String password;
    private String username;
    private String companyName;
}
