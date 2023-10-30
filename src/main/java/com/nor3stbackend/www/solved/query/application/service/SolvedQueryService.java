package com.nor3stbackend.www.solved.query.application.service;

import com.nor3stbackend.www.config.SecurityUtil;
import com.nor3stbackend.www.solved.query.application.vo.DailyTaskVO;
import com.nor3stbackend.www.solved.query.infra.mapper.SolvedMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    public List<DailyTaskVO> getMyDailyTask() {
        List<DailyTaskVO> dailyTaskVOList = solvedMapper.getMyDailyTask(SecurityUtil.getCurrentMemberId());

        return dailyTaskVOList;
    }

    public FileSystemResource getMyDailyTaskAudio(String audioUrl) {
        File file = new File(uploadPath + audioUrl);
        return new FileSystemResource(file);
    }
}
