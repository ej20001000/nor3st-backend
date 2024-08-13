package com.nor3stbackend.www.solved.query.application.controller;

import com.nor3stbackend.www.common.ResponseMessage;
import com.nor3stbackend.www.solved.query.application.service.SolvedQueryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolvedQueryController {

    private final SolvedQueryService solvedQueryService;

    public SolvedQueryController(SolvedQueryService solvedQueryService) {
        this.solvedQueryService = solvedQueryService;
    }

    @GetMapping("/solved/speaking")
    @Operation(summary = "데일리 스피킹 문제 조회")
    public ResponseEntity<ResponseMessage> getMySolvedListSpeaking() {

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, "success", solvedQueryService.getMyDailyTaskSpeaking());

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }

    @GetMapping("/solved/listening")
    @Operation(summary = "데일리 리스닝 문제 조회")
    public ResponseEntity<ResponseMessage> getMySolvedListListening() {

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, "success", solvedQueryService.getMyDailyTaskListening());

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }

    @GetMapping("/solved/audio")
    @Operation(summary = "문제 관련 음성 파일 조회")
    public ResponseEntity<?> getMySolvedAudio(@RequestParam String audioUrl) {
        FileSystemResource response = solvedQueryService.getMyDailyTaskAudio(audioUrl);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "audio/mpeg");

        return ResponseEntity.ok()
                .headers(headers)
                .body(response);
    }

    @GetMapping("/solvedPercentage")
    @Operation(summary = "문제 해결 퍼센티지 조회(어드민 통계용)")
    public ResponseEntity<ResponseMessage> getSolvedPercentage() {
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, "success", solvedQueryService.getCompanyTaskPercentage());

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }
}
