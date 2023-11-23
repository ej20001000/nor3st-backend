package com.nor3stbackend.www.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String memberRole;
}
