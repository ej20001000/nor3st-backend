package com.nor3stbackend.www.member.command.application.service;

import com.nor3stbackend.www.company.command.application.service.CompanyService;
import com.nor3stbackend.www.config.JwtTokenProvider;
import com.nor3stbackend.www.config.SecurityUtil;
import com.nor3stbackend.www.login.Encryptor;
import com.nor3stbackend.www.login.TokenInfo;
import com.nor3stbackend.www.member.command.application.dto.EmployeeUpdateDto;
import com.nor3stbackend.www.member.command.application.dto.ManagerRegistrationDto;
import com.nor3stbackend.www.member.command.domain.RoleEnum;
import com.nor3stbackend.www.member.command.domain.aggregate.MemberEntity;
import com.nor3stbackend.www.member.command.infra.repository.MemberRepository;
import com.nor3stbackend.www.member.query.infra.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final CompanyService companyService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final Encryptor encryptor;

    @Transactional
    public TokenInfo login(String username, String password) {

        Optional<MemberEntity> memberEntity = memberRepository.findByUsername(username);

        if (memberEntity.isEmpty()) {
            throw new BadCredentialsException("존재하지 않는 회원입니다.");
        }

        if(BCrypt.checkpw(password, memberEntity.get().getPassword())) {
            // 1. Login ID/PW를 기반으로 Authentication 객체 생성
            // 이때 authentication은 인증 여부를 확인하는 authenticated 값이 false
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, memberEntity.get().getPassword());

            // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
            // authenticate 메서드가 실행될 때 CustomUserDetailsService에서 만든 loadUserByUsername 메서드가 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);


            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

            tokenInfo.setMemberRole(memberEntity.get().getRoles().get(0));

            return tokenInfo;
        }  else {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
    }


    @Transactional
    public List<String> registerMultipleEmployee(Integer registerCount) {
        List<String> result = new ArrayList<>();

        Random random = new Random();


        Long companyId = memberRepository.getReferenceById(SecurityUtil.getCurrentMemberId())
                .getCompanyEntity().getCompanyId();

        Integer employeeCount= memberMapper.countByCompanyId(companyId);

        if(employeeCount > 100) {
            throw new RuntimeException("사원 수가 100명을 초과하였습니다.");
        }

        for(int i = 0; i < registerCount; i++) {
            // 8자리 랜덤 숫자 생성
            int max = 99999999;
            int min = 10000000;
            int randomNumber = random.nextInt(max - min + 1) + min;

            String username = String.valueOf(randomNumber);
            String password = String.valueOf(randomNumber);

            MemberEntity memberEntity = MemberEntity.builder()
                    .username(username)
                    .password(encryptor.encrypt(password))
                    .companyEntity(companyService.getCompany(companyId).get())
                    .roles(Collections.singletonList(RoleEnum.EMPLOYEE.name()))
                    .build();

            memberRepository.save(memberEntity);

            result.add(username);
        }

        return result;
    }

    @Transactional
    public void updateEmployee(EmployeeUpdateDto employeeUpdateDto) {
        MemberEntity memberEntity = memberRepository.getReferenceById(SecurityUtil.getCurrentMemberId());

        memberEntity.updateEmployee(employeeUpdateDto.getUsername(), encryptor.encrypt(employeeUpdateDto.getPassword()), employeeUpdateDto.getDepartment());

        memberRepository.save(memberEntity);
    }

    // 일괄 생성으로 변경

//    @Transactional
//    public Long registerEmployee(EmployeeRegistrationDto employeeRegistrationDto) {
//
//
//        MemberEntity memberEntity = MemberEntity.builder()
//                .username(employeeRegistrationDto.getUsername())
//                .password(encryptor.encrypt(employeeRegistrationDto.getPassword()))
//                .companyEntity(companyService.getCompany(employeeRegistrationDto.getCompanyId()).get())
//                .employeeName(employeeRegistrationDto.getEmployeeName())
//                .companyPosition(employeeRegistrationDto.getCompanyPosition())
//                .department(employeeRegistrationDto.getDepartment())
//                .roles(Collections.singletonList(RoleEnum.EMPLOYEE.name()))
//                .build();
//
//        solvedService.createDailyTask(memberEntity);
//
//        return memberRepository.save(memberEntity).getMemberId();
//    }

    @Transactional
    public MemberEntity registerManager(ManagerRegistrationDto managerRegistrationDto) {
        MemberEntity memberEntity = MemberEntity.builder()
                .username(managerRegistrationDto.getUsername())
                .password(encryptor.encrypt(managerRegistrationDto.getPassword()))
                .companyEntity(companyService.insertCompany(managerRegistrationDto.getCompanyName()))
                .department(managerRegistrationDto.getDepartment())
                .roles(Collections.singletonList(RoleEnum.MANAGER.name()))
                .build();

        return memberRepository.save(memberEntity);
    }

    public boolean checkUsername(String username) {

        return memberRepository.findByUsername(username).isEmpty();
    }

    public MemberEntity getMember(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    public MemberEntity getMemberByUsername(String username) {
        Optional<MemberEntity> memberEntity = memberRepository.findByUsername(username);

        if(memberEntity.isEmpty()) {
            throw new RuntimeException("유저가 존재하지 않습니다!");
        }
        return memberEntity.get();
    }
}
