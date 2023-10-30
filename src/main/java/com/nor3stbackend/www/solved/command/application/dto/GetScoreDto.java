package com.nor3stbackend.www.solved.command.application.dto;

import lombok.Data;

@Data
public class GetScoreDto {
    private String answer;
    private int score;
}
