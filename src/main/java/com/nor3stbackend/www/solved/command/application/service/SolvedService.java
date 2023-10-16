package com.nor3stbackend.www.solved.command.application.service;

import com.nor3stbackend.www.solved.command.infra.repository.SolvedRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class SolvedService {

    private final SolvedRepository solvedRepository;
//    private final Path rootLocation;

    public SolvedService(SolvedRepository solvedRepository) {
        this.solvedRepository = solvedRepository;
    }

    @Transactional
    public void insertSolved(MultipartFile file) {

//        try {
//            if(file.isEmpty()) {
//                throw new Exception("파일이 존재하지 않습니다.");
//            }
//
//            // https://spring.io/guides/gs/uploading-files/
//            Path path = this.rootLocation.resolve(Paths.get(file.getOriginalFilename()))
//                    .normalize().toAbsolutePath();
//        }

    }
}
