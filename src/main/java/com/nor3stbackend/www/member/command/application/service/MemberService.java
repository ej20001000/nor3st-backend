package com.nor3stbackend.www.member.command.application.service;

import com.nor3stbackend.www.company.command.application.service.CompanyService;
import com.nor3stbackend.www.config.JwtTokenProvider;
import com.nor3stbackend.www.login.Encryptor;
import com.nor3stbackend.www.login.TokenInfo;
import com.nor3stbackend.www.member.command.application.dto.EmployeeRegistrationDto;
import com.nor3stbackend.www.member.command.application.dto.ManagerRegistrationDto;
import com.nor3stbackend.www.member.command.domain.RoleEnum;
import com.nor3stbackend.www.member.command.domain.aggregate.MemberEntity;
import com.nor3stbackend.www.member.command.infra.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CompanyService companyService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final Encryptor encryptor;

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
    public Long registerEmployee(EmployeeRegistrationDto employeeRegistrationDto) {


        MemberEntity memberEntity = MemberEntity.builder()
                .username(employeeRegistrationDto.getUsername())
                .password(encryptor.encrypt(employeeRegistrationDto.getPassword()))
                .companyEntity(companyService.getCompany(employeeRegistrationDto.getCompanyId()).get())
                .employeeNo(employeeRegistrationDto.getEmployeeNo())
                .companyPosition(employeeRegistrationDto.getCompanyPosition())
                .department(employeeRegistrationDto.getDepartment())
                .roles(Collections.singletonList(RoleEnum.EMPLOYEE.name()))
                .build();

        return memberRepository.save(memberEntity).getMemberId();
    }

    @Transactional
    public MemberEntity registerManager(ManagerRegistrationDto managerRegistrationDto) {
        MemberEntity memberEntity = MemberEntity.builder()
                .username(managerRegistrationDto.getUsername())
                .password(encryptor.encrypt(managerRegistrationDto.getPassword()))
                .companyEntity(companyService.insertCompany(managerRegistrationDto.getCompanyName()))
                .build();

        return memberRepository.save(memberEntity);
    }

}
