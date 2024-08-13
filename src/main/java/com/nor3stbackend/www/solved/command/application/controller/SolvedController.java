package com.nor3stbackend.www.solved.command.application.controller;

import com.nor3stbackend.www.common.ResponseMessage;
import com.nor3stbackend.www.solved.command.application.service.SolvedService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class SolvedController {

    private final SolvedService solvedService;

    public SolvedController(SolvedService solvedService) {
        this.solvedService = solvedService;
    }

    @PostMapping(value = "/solved/speaking/{solvedId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "스피킹 문제 관련 답 제출 후 판독 및 로그 생성")
    public ResponseEntity<ResponseMessage> insertSpeakingSolved(@RequestBody MultipartFile file, @PathVariable Long solvedId) {


        ResponseMessage responseMessage = solvedService.insertSpeakingSolved(file, solvedId);

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }

    @PostMapping("/solved/listening/{solvedId}")
    @Operation(summary = "리스닝 문제 관련 답 제출 후 판독 및 로그 생성")
    public ResponseEntity<ResponseMessage> insertListeningSolved(@RequestParam boolean isAnswer, @PathVariable Long solvedId) {

        solvedService.insertListeningSolved(isAnswer, solvedId);

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, "success", null);

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }

    @PostMapping("solved/batchDailyTask")
    @Operation(summary = "데일리 테스크 수동 배치 API")
    public ResponseEntity<ResponseMessage> insertBatchDailyTask() {
        ResponseMessage responseMessage;
        try {
            solvedService.insertBatchDailyTask();
            responseMessage = new ResponseMessage(HttpStatus.ACCEPTED, "Daily Task Insert 성공", null);
        } catch(RuntimeException e) {
            log.error(e.getMessage());
            responseMessage = new ResponseMessage(HttpStatus.CONFLICT, e.getMessage(), null);
        }
        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }
}
