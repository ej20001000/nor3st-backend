package com.nor3stbackend.www.member.query.domain.vo;

import lombok.Data;

@Data
public class MemberListVO {
    private Long memberId;
    private String username;
    private String department;
}
