package com.nor3stbackend.www.member.query.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberQueryDto {
    private String username;
    private String department;
}
