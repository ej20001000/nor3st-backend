package com.nor3stbackend.www.problem.command.application.service;

import com.nor3stbackend.www.problem.command.application.dto.ProblemCreateDto;
import com.nor3stbackend.www.problem.command.domain.aggregate.ProblemEntity;
import com.nor3stbackend.www.problem.command.infra.repository.ProblemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class ProblemService {

    private final ProblemRepository problemRepository;

    @Value("${upload.problem.path}")
    private String uploadPath;

    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @Transactional
    public ProblemEntity createProblem(ProblemCreateDto problemCreateDto) {

        try {
            MultipartFile file = problemCreateDto.getAudioFile();

            // 파일 저장 경로 설정
            String origName = file.getOriginalFilename();
            String extension = origName.substring(origName.lastIndexOf("."));

            if(!extension.equals(".mp3")) {
                throw new IllegalArgumentException("mp3 파일만 업로드 가능합니다.");
            }

            String uniqueName = UUID.randomUUID() + extension;

            //파일 저장
            File dest = new File(uploadPath + uniqueName);
            file.transferTo(dest);

            ProblemEntity problemEntity = new ProblemEntity(problemCreateDto.getKoreanContent(),
                    problemCreateDto.getVietContent(), uniqueName);

            return problemRepository.save(problemEntity);

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("파일 저장에 실패하였습니다.");
        }

    }
}
