package com.nor3stbackend.www.problem.command.application.controller;

import com.nor3stbackend.www.common.ResponseMessage;
import com.nor3stbackend.www.problem.command.application.dto.ProblemCreateDto;
import com.nor3stbackend.www.problem.command.application.service.ProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class ProblemController {

    private final ProblemService problemService;

    ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @PostMapping("/problem")
    public ResponseEntity<?> createProblem(@ModelAttribute ProblemCreateDto problemCreateDto) {
        ResponseMessage responseMessage;

        try{
            responseMessage = new ResponseMessage(HttpStatus.ACCEPTED, "problem successfully created!", problemService.createProblem(problemCreateDto));
        } catch (IllegalArgumentException e) {
            responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST, e.getMessage(), null);
            log.error(e.getMessage());
        }

        return new ResponseEntity<>(responseMessage, responseMessage.getStatus());
    }
}
