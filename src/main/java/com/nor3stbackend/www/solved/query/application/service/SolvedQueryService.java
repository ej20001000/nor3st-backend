package com.nor3stbackend.www.solved.query.application.service;

import com.nor3stbackend.www.config.SecurityUtil;
import com.nor3stbackend.www.solved.command.domain.aggregate.SolvedEntity;
import com.nor3stbackend.www.solved.query.infra.mapper.SolvedMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@Slf4j
public class SolvedQueryService {

    private final SolvedMapper solvedMapper;

    @Value("{upload.problem.path}")
    private String uploadPath;

    public SolvedQueryService (SolvedMapper solvedMapper) {
        this.solvedMapper = solvedMapper;
    }

    public SolvedEntity getMyDailyTask() {
        try {
            SolvedEntity solvedEntity = solvedMapper.getMyDailyTask(SecurityUtil.getCurrentMemberId());

            File file = new File(uploadPath + solvedEntity.getProblemEntity().getAudioUrl());
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return solvedEntity;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("파일 읽기 실패");
        }
    }
}
