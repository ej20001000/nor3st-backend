package com.nor3stbackend.www.member.command.application.controller;

import com.nor3stbackend.www.common.ResponseMessage;
import com.nor3stbackend.www.config.SecurityUtil;
import com.nor3stbackend.www.login.TokenInfo;
import com.nor3stbackend.www.member.command.application.dto.EmployeeRegistrationDto;
import com.nor3stbackend.www.member.command.application.dto.ManagerRegistrationDto;
import com.nor3stbackend.www.member.command.application.dto.MemberLoginRequestDto;
import com.nor3stbackend.www.member.command.application.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = memberService.login(memberId, password);
        return tokenInfo;
    }

    @PostMapping("/employee")
    public ResponseEntity<?> registerEmployee(@RequestBody EmployeeRegistrationDto employeeRegistrationDto) {
        ResponseMessage responseMessage = new ResponseMessage();

        try {
            responseMessage.setData(memberService.registerEmployee(employeeRegistrationDto));
            responseMessage.setMessage("사원이 정상적으로 생성되었습니다.");
            responseMessage.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseMessage.setMessage("사원 생성에 실패하였습니다.");
            responseMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseMessage.setData(e.getMessage());
        }


        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());

    }

    @PostMapping("/manager")
    public ResponseEntity<?> registerManager(@RequestBody ManagerRegistrationDto managerRegistrationDto) {
        ResponseMessage responseMessage = new ResponseMessage();

        try {
            responseMessage.setData(memberService.registerManager(managerRegistrationDto));
            responseMessage.setMessage("매니저가 정상적으로 생성되었습니다.");
            responseMessage.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseMessage.setMessage("매니저 생성에 실패하였습니다.");
            responseMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseMessage.setData(e.getMessage());
        }

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }
}
