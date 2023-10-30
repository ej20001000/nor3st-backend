package com.nor3stbackend.www.solved.query.application.service;

import com.nor3stbackend.www.config.SecurityUtil;
import com.nor3stbackend.www.solved.command.domain.aggregate.SolvedEntity;
import com.nor3stbackend.www.solved.query.application.dto.DailyTaskVO;
import com.nor3stbackend.www.solved.query.infra.mapper.SolvedMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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
        try {
            List<DailyTaskVO> dailyTaskVOList = solvedMapper.getMyDailyTask(SecurityUtil.getCurrentMemberId());

            for (DailyTaskVO dailyTaskVO : dailyTaskVOList) {
                File file = new File(uploadPath + dailyTaskVO.getAudioUrl());
                byte[] fileContent = Files.readAllBytes(file.toPath());
                dailyTaskVO.setAudioContent(fileContent);
            }

            return dailyTaskVOList;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("파일 읽기 실패");
        }
    }
}
