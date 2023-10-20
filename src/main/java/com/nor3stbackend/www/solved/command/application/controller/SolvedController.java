package com.nor3stbackend.www.solved.command.application.controller;

import com.nor3stbackend.www.common.ResponseMessage;
import com.nor3stbackend.www.solved.command.application.service.SolvedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class SolvedController {

    private final SolvedService solvedService;

    public SolvedController(SolvedService solvedService) {
        this.solvedService = solvedService;
    }

    @PostMapping("/solved")
    public ResponseEntity<?> insertSolved(@RequestBody MultipartFile file) {

        ResponseMessage responseMessage = solvedService.insertSolved(file);

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }
}
