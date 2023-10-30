package com.nor3stbackend.www.solved.command.domain.vo;

import com.nor3stbackend.www.solved.command.domain.enumType.SolvedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertSolvedResponseVO {
    private int score;
    private String isSolved;
    private String answer;
}
