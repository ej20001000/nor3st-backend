package com.nor3stbackend.www.problem.command.application.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProblemCreateDto {
    private MultipartFile audioFile;
    private String koreanContent;
    private String vietContent;
}
