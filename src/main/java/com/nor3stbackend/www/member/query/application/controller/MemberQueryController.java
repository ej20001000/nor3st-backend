package com.nor3stbackend.www.member.query.application.controller;

import com.nor3stbackend.www.common.ResponseMessage;
import com.nor3stbackend.www.config.SecurityUtil;
import com.nor3stbackend.www.member.query.application.service.MemberQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberQueryController {

    private final MemberQueryService memberQueryService;

    public MemberQueryController(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    @GetMapping("/members")
    public ResponseEntity<?> getMembers() {
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK,
                "회원 정보 조회에 성공하였습니다.",
                memberQueryService.findByMemberId(SecurityUtil.getCurrentMemberId()));

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }

    @GetMapping("/employeeList/{page}")
    public ResponseEntity<?> getEmployeeList(@PathVariable int page) {

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, "사원 목록 조회에 성공하였습니다.", memberQueryService.getEmployeeList(page));

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }

    @GetMapping("/admin/dashboard")
    public ResponseEntity<?> getAdminDashboardInfo() {
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, "관리자 대시보드 정보 조회에 성공하였습니다.", memberQueryService.getAdminDashboardInfo());

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }
}
