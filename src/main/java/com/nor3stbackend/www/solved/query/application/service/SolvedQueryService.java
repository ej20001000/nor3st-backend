package com.nor3stbackend.www.solved.query.application.service;

import com.nor3stbackend.www.config.SecurityUtil;
import com.nor3stbackend.www.member.query.infra.mapper.MemberMapper;
import com.nor3stbackend.www.solved.query.application.vo.DailyTaskListeningVO;
import com.nor3stbackend.www.solved.query.application.vo.DailyTaskVO;
import com.nor3stbackend.www.solved.query.application.vo.TaskProgressPercentageVO;
import com.nor3stbackend.www.solved.query.infra.mapper.SolvedMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SolvedQueryService {

    private final SolvedMapper solvedMapper;
    private final MemberMapper memberMapper;

    @Value("${upload.problem.path}")
    private String uploadPath;

    public List<DailyTaskVO> getMyDailyTaskSpeaking() {
        return solvedMapper.getMyDailyTask(SecurityUtil.getCurrentMemberId());
    }

    public List<DailyTaskListeningVO> getMyDailyTaskListening() {

        List<DailyTaskVO> dailyTaskVOList = solvedMapper.getMyDailyTask(SecurityUtil.getCurrentMemberId());
        List<DailyTaskListeningVO> dailyTaskListeningVOList = new ArrayList<>();

        for(DailyTaskVO dailyTaskVO : dailyTaskVOList) {
            List<String> questionList = solvedMapper.getMyDailyTaskListeningWrongVietList(dailyTaskVO.getProblemId());
            questionList.add(dailyTaskVO.getVietContent());
            Collections.shuffle(questionList);
            DailyTaskListeningVO dailyTaskListeningVO = new DailyTaskListeningVO(dailyTaskVO, questionList);
            dailyTaskListeningVOList.add(dailyTaskListeningVO);
        }

        return dailyTaskListeningVOList;
    }

    // 사원 참여도(풀이 시도율)
    public double getCompanyDailySolvedRate(Long companyId) {
        return solvedMapper.getCompanyDailySolvedRate(companyId);
    }

    // 사원 데일리 평균 점수(풀이 시도중)
    public double getCompanyDailySolvedAvgScore(Long companyId) {
        return solvedMapper.getCompanyDailySolvedAvgScore(companyId);
    }

    // 데일리 풀이 시도 사원 수
    public int getCompanyDailySolvedEmployeeCount(Long companyId) {
        return solvedMapper.getCompanyDailySolvedEmployeeCount(companyId);
    }

    public FileSystemResource getMyDailyTaskAudio(String audioUrl) {
        File file = new File(uploadPath + audioUrl);
        return new FileSystemResource(file);
    }

    public MultiValueMap<String, TaskProgressPercentageVO> getCompanyTaskPercentage() {

        Long companyId = memberMapper.getCompanyId(SecurityUtil.getCurrentMemberId());


        MultiValueMap<String, TaskProgressPercentageVO> data = new LinkedMultiValueMap<>();
        data.add("daily", solvedMapper.getCompanyTaskPercentageDaily(companyId));
        data.add("weekly", solvedMapper.getCompanyTaskPercentageWeekly(companyId));
        data.add("monthly", solvedMapper.getCompanyTaskPercentageMonthly(companyId));

        return data;
    }

    public TaskProgressPercentageVO getCompanyTaskPercentageDaily(Long companyId) {
        return solvedMapper.getCompanyTaskPercentageDaily(companyId);
    }

    public TaskProgressPercentageVO getCompanyTaskPercentageWeekly(Long companyId) {
        return solvedMapper.getCompanyTaskPercentageWeekly(companyId);
    }

    public TaskProgressPercentageVO getCompanyTaskPercentageMonthly(Long companyId) {
        return solvedMapper.getCompanyTaskPercentageMonthly(companyId);
    }

}
