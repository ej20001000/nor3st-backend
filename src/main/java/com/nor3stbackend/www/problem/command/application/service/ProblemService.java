package com.nor3stbackend.www.problem.command.application.service;

import com.nor3stbackend.www.problem.command.application.dto.ProblemCreateDto;
import com.nor3stbackend.www.problem.command.application.dto.ProblemCreateWithAIDto;
import com.nor3stbackend.www.problem.command.domain.aggregate.ProblemEntity;
import com.nor3stbackend.www.problem.command.infra.repository.ProblemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
@Slf4j
public class ProblemService {

    private final ProblemRepository problemRepository;

    @Value("${upload.problem.path}")
    private String uploadPath;

    @Value("${ai.url}")
    private String aiUrl;

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

    @Transactional
    public ProblemEntity createProblemWithAI(String korean) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("korean", korean);

        HttpEntity<?> request = new HttpEntity<>(body, headers);
        ResponseEntity<ProblemCreateWithAIDto> response = restTemplate.postForEntity(aiUrl + "/get_sentence2voice_viet", request, ProblemCreateWithAIDto.class);

        ProblemCreateWithAIDto problemCreateWithAIDto = response.getBody();

        String fileName = UUID.randomUUID() + ".mp3";

        byte[] decodedBytes = Base64.getDecoder().decode(problemCreateWithAIDto.getAudio());

        try {
            Path path = Paths.get(uploadPath + fileName);
            Files.write(path, decodedBytes);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("file was not created properly");
        }

        ProblemEntity problemEntity = new ProblemEntity(korean, problemCreateWithAIDto.getVietnamese(), fileName);

        return problemRepository.save(problemEntity);
    }
}
