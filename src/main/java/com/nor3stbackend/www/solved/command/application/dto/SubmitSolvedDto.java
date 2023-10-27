package com.nor3stbackend.www.solved.command.application.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SubmitSolvedDto {
    private Long solvedId;
    private MultipartFile file;
}
