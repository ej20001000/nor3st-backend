package com.nor3stbackend.www.solved.command.application.service;

import com.nor3stbackend.www.common.ResponseMessage;
import com.nor3stbackend.www.config.SecurityConfig;
import com.nor3stbackend.www.config.SecurityUtil;
import com.nor3stbackend.www.member.command.application.service.MemberService;
import com.nor3stbackend.www.member.command.domain.aggregate.MemberEntity;
import com.nor3stbackend.www.solved.command.domain.aggregate.SolvedEntity;
import com.nor3stbackend.www.solved.command.infra.repository.SolvedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
@Slf4j
public class SolvedService {

    @Value("${upload.path}")
    private String uploadPath;

    private final SolvedRepository solvedRepository;
    private final MemberService memberService;


    public SolvedService(SolvedRepository solvedRepository, MemberService memberService) {
        this.solvedRepository = solvedRepository;
        this.memberService = memberService;
    }

    @Transactional
    public ResponseMessage insertSolved(MultipartFile file) {

        ResponseMessage responseMessage;

        try {
            String origName = file.getOriginalFilename();
            String extension = origName.substring(origName.lastIndexOf("."));
            String fullPath = uploadPath + UUID.randomUUID() + extension;
            file.transferTo(new File(fullPath));

            MemberEntity memberEntity = memberService.getMember(Long.parseLong(SecurityUtil.getCurrentMemberId()));

            SolvedEntity solvedEntity = new SolvedEntity(memberEntity, fullPath);

            solvedRepository.save(solvedEntity);

            responseMessage = new ResponseMessage(HttpStatus.OK, "파일 업로드에 성공하였습니다.", solvedEntity);
        } catch (IOException e) {
            log.error(e.getMessage());
            responseMessage = new ResponseMessage(HttpStatus.OK, "파일 업로드에 실패하였습니다.", e.getMessage());
        }

        return responseMessage;
    }
}
