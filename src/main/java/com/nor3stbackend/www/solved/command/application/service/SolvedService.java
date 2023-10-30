package com.nor3stbackend.www.solved.command.application.service;

import com.nor3stbackend.www.common.ResponseMessage;
import com.nor3stbackend.www.member.command.domain.aggregate.MemberEntity;
import com.nor3stbackend.www.problem.command.domain.aggregate.ProblemEntity;
import com.nor3stbackend.www.problem.query.application.service.ProblemQueryService;
import com.nor3stbackend.www.solved.command.application.dto.GetScoreDto;
import com.nor3stbackend.www.solved.command.domain.aggregate.SolvedEntity;
import com.nor3stbackend.www.solved.command.domain.aggregate.SolvedHistoryEntity;
import com.nor3stbackend.www.solved.command.domain.enumType.SolvedEnum;
import com.nor3stbackend.www.solved.command.domain.vo.InsertSolvedResponseVO;
import com.nor3stbackend.www.solved.command.infra.repository.SolvedHistoryRepository;
import com.nor3stbackend.www.solved.command.infra.repository.SolvedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@Slf4j
public class SolvedService {

    @Value("${upload.solved.path}")
    private String uploadPath;

    @Value("${ai.url}")
    private String aiUrl;

    private final SolvedRepository solvedRepository;
    private final SolvedHistoryRepository solvedHistoryRepository;
    private final ProblemQueryService problemQueryService;


    public SolvedService(SolvedRepository solvedRepository, SolvedHistoryRepository solvedHistoryRepository, ProblemQueryService problemQueryService) {
        this.solvedRepository = solvedRepository;
        this.solvedHistoryRepository = solvedHistoryRepository;
        this.problemQueryService = problemQueryService;
    }

    @Transactional
    public ResponseMessage insertSolved(MultipartFile file, Long solvedId) {

        ResponseMessage responseMessage;

        SolvedEntity solvedEntity = solvedRepository.getReferenceById(solvedId);

        try {

            // 파일 저장 경로 설정
            String origName = file.getOriginalFilename();
            String extension = origName.substring(origName.lastIndexOf("."));
            String uniquePath = UUID.randomUUID() + extension;
            String fullPath = uploadPath + uniquePath;

            //파일 저장
            File dest = new File(fullPath);
            file.transferTo(dest);

            FileSystemResource resource = new FileSystemResource(dest);

            // AI 서버로 요청
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "multipart/form-data");

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("voice", resource);
            body.add("script", solvedEntity.getProblemEntity().getKoreanContent());

            GetScoreDto getScoreDto = requestToAI(headers, body);

            // DB 저장

            SolvedEnum solvedEnum;

            int score = getScoreDto.getScore();
            System.out.println(getScoreDto.getAnswer());

            if (score >= 80) {
                solvedEnum = SolvedEnum.SOLVED;
            } else {
                solvedEnum = SolvedEnum.WRONG;
            }

            solvedEntity.updateSolved(uniquePath, solvedEnum, score);
            SolvedHistoryEntity solvedHistoryEntity = new SolvedHistoryEntity(solvedRepository.save(solvedEntity), uniquePath, solvedEnum);

            solvedHistoryRepository.save(solvedHistoryEntity);

            responseMessage = new ResponseMessage(HttpStatus.OK, "파일 업로드에 성공하였습니다.", new InsertSolvedResponseVO(score, solvedEnum.name(), getScoreDto.getAnswer()));
        } catch (IOException e) {
            log.error(e.getMessage());
            responseMessage = new ResponseMessage(HttpStatus.OK, "파일 업로드에 실패하였습니다.", e.getMessage());
        }

        return responseMessage;
    }

    public GetScoreDto requestToAI(HttpHeaders headers, MultiValueMap<String, Object> body) {
        RestTemplate restTemplate = new RestTemplate();


        HttpEntity<?> request = new HttpEntity<>(body, headers);
        ResponseEntity<GetScoreDto> response = restTemplate.postForEntity(aiUrl + "/get_score", request, GetScoreDto.class);

        return response.getBody();
    }

    // 회원 가입 시 돌아갈 문제 생성
    @Transactional
    public void createDailyTask(MemberEntity memberEntity) {

        List<ProblemEntity> dailyTask = problemQueryService.getDailyProblem();

        for(ProblemEntity problemEntity : dailyTask) {
            SolvedEntity solvedEntity = new SolvedEntity(memberEntity, problemEntity);
            solvedRepository.save(solvedEntity);
        }
    }
}
