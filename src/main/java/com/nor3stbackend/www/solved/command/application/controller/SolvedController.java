package com.nor3stbackend.www.solved.command.application.controller;

import com.nor3stbackend.www.common.ResponseMessage;
import com.nor3stbackend.www.solved.command.application.service.SolvedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/solved/speaking/{solvedId}")
    public ResponseEntity<?> insertSpeakingSolved(@RequestBody MultipartFile file, @PathVariable Long solvedId) {


        ResponseMessage responseMessage = solvedService.insertSpeakingSolved(file, solvedId);

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }

    @PostMapping("/solved/listening/{solvedId}")
    public ResponseEntity<?> insertListeningSolved(@RequestParam boolean isAnswer, @PathVariable Long solvedId) {

        solvedService.insertListeningSolved(isAnswer, solvedId);

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, "success", null);

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }

    @PostMapping("solved/dailyTask")
    public ResponseEntity<?> insertDailyTask(@RequestParam String username) {
        ResponseMessage responseMessage;
        try {
            solvedService.createDailyTask(username);
            responseMessage = new ResponseMessage(HttpStatus.ACCEPTED, "Daily Task Insert 성공", null);
        } catch(RuntimeException e) {
            log.error(e.getMessage());
            responseMessage = new ResponseMessage(HttpStatus.CONFLICT, e.getMessage(), null);
        }
        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }
}
