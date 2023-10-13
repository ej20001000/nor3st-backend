package com.nor3stbackend.www.member.command.application.service;

import com.nor3stbackend.www.company.command.application.service.CompanyService;
import com.nor3stbackend.www.company.command.domain.aggregate.CompanyEntity;
import com.nor3stbackend.www.config.JwtTokenProvider;
import com.nor3stbackend.www.login.TokenInfo;
import com.nor3stbackend.www.member.command.application.dto.EmployeeRegistrationDto;
import com.nor3stbackend.www.member.command.domain.aggregate.MemberEntity;
import com.nor3stbackend.www.member.command.infra.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CompanyService companyService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenInfo login(String memberId, String password) {
        // 1. Login ID/PW를 기반으로 Authentication 객체 생성
        // 이때 authentication은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 메서드가 실행될 때 CustomUserDetailsService에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

    @Transactional
    public void registerEmployee(EmployeeRegistrationDto employeeRegistrationDto) {

        CompanyEntity companyEntity = checkIfEmpty(companyService.getCompany(employeeRegistrationDto.getCompanyName()), employeeRegistrationDto.getCompanyName());

        MemberEntity memberEntity = MemberEntity.builder()
                .username(employeeRegistrationDto.getUsername())
                .password(employeeRegistrationDto.getPassword())
                .companyEntity(companyEntity)
                .employeeNo(employeeRegistrationDto.getEmployeeNo())
                .build();
    }

    public CompanyEntity checkIfEmpty(Optional<CompanyEntity> companyEntity, String companyName) {
        if(companyEntity.isEmpty()) {
            return companyService.insertCompany(companyName);
        }
        return companyEntity.get();
    }
}
