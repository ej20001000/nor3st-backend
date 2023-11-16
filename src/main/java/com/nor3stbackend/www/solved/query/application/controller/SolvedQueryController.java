package com.nor3stbackend.www.solved.query.application.controller;

import com.nor3stbackend.www.common.ResponseMessage;
import com.nor3stbackend.www.solved.query.application.service.SolvedQueryService;
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
    public ResponseEntity<?> getMySolvedListSpeaking() {

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, "success", solvedQueryService.getMyDailyTaskSpeaking());

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }

    @GetMapping("/solved/listening")
    public ResponseEntity<?> getMySolvedListListening() {

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, "success", solvedQueryService.getMyDailyTaskListening());

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }

    @GetMapping("/solved/audio")
    public ResponseEntity<?> getMySolvedAudio(@RequestParam String audioUrl) {
        FileSystemResource response = solvedQueryService.getMyDailyTaskAudio(audioUrl);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "audio/mpeg");

        return ResponseEntity.ok()
                .headers(headers)
                .body(response);
    }

    @GetMapping("/solvedPercentage")
    public ResponseEntity<?> getSolvedPercentage() {
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, "success", solvedQueryService.getCompanyTaskPercentage());

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }
}
