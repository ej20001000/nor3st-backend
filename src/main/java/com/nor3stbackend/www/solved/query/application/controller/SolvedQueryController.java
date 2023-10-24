package com.nor3stbackend.www.solved.query.application.controller;

import com.nor3stbackend.www.common.ResponseMessage;
import com.nor3stbackend.www.solved.query.application.service.SolvedQueryService;
import com.nor3stbackend.www.solved.query.infra.mapper.SolvedMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolvedQueryController {

    private final SolvedQueryService solvedQueryService;

    public SolvedQueryController(SolvedQueryService solvedQueryService) {
        this.solvedQueryService = solvedQueryService;
    }

    @GetMapping("/solved")
    public ResponseEntity<?> getMySolvedList() {

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, "success", solvedQueryService.getMySolvedList());

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}
