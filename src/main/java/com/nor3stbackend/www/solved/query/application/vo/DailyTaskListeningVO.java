package com.nor3stbackend.www.solved.query.application.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DailyTaskListeningVO {
    private DailyTaskVO dailyTaskVO;
    private List<String> questionList;

}
