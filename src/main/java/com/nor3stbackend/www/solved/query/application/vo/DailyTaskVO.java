package com.nor3stbackend.www.solved.query.application.vo;

import lombok.Data;

@Data
public class DailyTaskVO {
    private Long solvedId;
    private String isSolved;
    private Integer solvedScore;
    private String koreanContent;
    private String vietContent;
    private String audioUrl;
    private Long problemId;
}