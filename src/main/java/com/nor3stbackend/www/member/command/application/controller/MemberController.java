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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        ResponseMessage responseMessage;
        try{
            String username = memberLoginRequestDto.getUsername();
            String password = memberLoginRequestDto.getPassword();
            TokenInfo tokenInfo = memberService.login(username, password);
            responseMessage = setResponseMessage("로그인에 성공하였습니다.", HttpStatus.OK, tokenInfo);
        } catch (BadCredentialsException e) {
            log.error(e.getMessage());
            responseMessage = setResponseMessage("로그인에 실패하였습니다.", HttpStatus.UNAUTHORIZED, e.getMessage());
        }

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }

    @PostMapping("/employee")
    public ResponseEntity<?> registerEmployee(@RequestBody EmployeeRegistrationDto employeeRegistrationDto) {
        ResponseMessage responseMessage;

        try {
            responseMessage = setResponseMessage("사원이 정상적으로 생성되었습니다.", HttpStatus.OK, memberService.registerEmployee(employeeRegistrationDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            responseMessage = setResponseMessage("사원 생성에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }


        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());

    }

    @PostMapping("/manager")
    public ResponseEntity<?> registerManager(@RequestBody ManagerRegistrationDto managerRegistrationDto) {
        ResponseMessage responseMessage = new ResponseMessage();

        try {
            responseMessage = setResponseMessage("매니저가 정상적으로 생성되었습니다.", HttpStatus.OK, memberService.registerManager(managerRegistrationDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            responseMessage = setResponseMessage("매니저 생성에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }

    @GetMapping("/checkUsername")
    public ResponseEntity<?> checkUsername(@RequestParam String username) {
        ResponseMessage responseMessage = new ResponseMessage();

        if(memberService.checkUsername(username)) {
            responseMessage = setResponseMessage("사용 가능한 아이디입니다.", HttpStatus.OK, true);
        } else {
            responseMessage = setResponseMessage("이미 사용중인 아이디입니다.", HttpStatus.OK, false);
        }

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }

    private ResponseMessage setResponseMessage(String message, HttpStatus status, Object data) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage(message);
        responseMessage.setStatus(status);
        responseMessage.setData(data);
        return responseMessage;
    }
}
