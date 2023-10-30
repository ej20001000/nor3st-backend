package com.nor3stbackend.www.problem.command.application.service;

import com.nor3stbackend.www.problem.command.application.dto.ProblemCreateDto;
import com.nor3stbackend.www.problem.command.domain.aggregate.ProblemEntity;
import com.nor3stbackend.www.problem.command.infra.repository.ProblemRepository;
import com.nor3stbackend.www.problem_audio.command.application.service.ProblemAudioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final ProblemAudioService problemAudioService;

    @Value("${upload.problem.path}")
    private String uploadPath;

    public ProblemService(ProblemRepository problemRepository, ProblemAudioService problemAudioService) {
        this.problemRepository = problemRepository;
        this.problemAudioService = problemAudioService;
    }

    @Transactional
    public ProblemEntity createProblem(ProblemCreateDto problemCreateDto) {
        List<MultipartFile> fileList = new ArrayList<>();
        fileList.add(problemCreateDto.getMaleAudioFile());
        fileList.add(problemCreateDto.getFemaleAudioFile());

        try {
            ProblemEntity problemEntity = new ProblemEntity(problemCreateDto.getKoreanContent(), problemCreateDto.getVietContent());
            for (int i = 0; i < fileList.size(); i++) {

                MultipartFile file = fileList.get(i);
                String gender;

                if(i == 0) gender = "M";
                else gender = "F";

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

                problemAudioService.createProblemAudio(gender, uniqueName, problemEntity);
            }


            return problemRepository.save(problemEntity);

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("파일 저장에 실패하였습니다.");
        }

    }
}
