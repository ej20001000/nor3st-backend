package com.nor3stbackend.www.solved.command.application.dto;

import lombok.Data;

@Data
public class GetScoreDto {

    public GetScoreDto(String answer, int score) {
        this.answer = answer;
        this.score = score;
    }

    private String answer;
    private int score;
}
