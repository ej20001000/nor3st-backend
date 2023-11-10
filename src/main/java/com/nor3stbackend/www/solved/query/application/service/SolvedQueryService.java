package com.nor3stbackend.www.solved.query.application.service;

import com.nor3stbackend.www.config.SecurityUtil;
import com.nor3stbackend.www.solved.query.application.vo.DailyTaskListeningVO;
import com.nor3stbackend.www.solved.query.application.vo.DailyTaskVO;
import com.nor3stbackend.www.solved.query.infra.mapper.SolvedMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class SolvedQueryService {

    private final SolvedMapper solvedMapper;

    @Value("${upload.problem.path}")
    private String uploadPath;

    public SolvedQueryService (SolvedMapper solvedMapper) {
        this.solvedMapper = solvedMapper;
    }

    public List<DailyTaskVO> getMyDailyTaskSpeaking() {
        List<DailyTaskVO> dailyTaskVOList = solvedMapper.getMyDailyTask(SecurityUtil.getCurrentMemberId());

        return dailyTaskVOList;
    }

    public List<DailyTaskListeningVO> getMyDailyTaskListening() {

        List<DailyTaskVO> dailyTaskVOList = solvedMapper.getMyDailyTask(SecurityUtil.getCurrentMemberId());
        List<DailyTaskListeningVO> dailyTaskListeningVOList = null;

        for(DailyTaskVO dailyTaskVO : dailyTaskVOList) {
            List<String> wrongVietList = solvedMapper.getMyDailyTaskListeningWrongVietList(dailyTaskVO.getProblemId());
            dailyTaskListeningVOList.add(new DailyTaskListeningVO(dailyTaskVO, wrongVietList));
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
}
