package com.nor3stbackend.www.problem.command.application.service;

import com.nor3stbackend.www.problem.command.application.dto.ProblemCreateDTO;
import com.nor3stbackend.www.problem.command.domain.aggregate.ProblemEntity;
import com.nor3stbackend.www.problem.command.infra.repository.ProblemRepository;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;

    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    public void createProblem(ProblemCreateDTO problemCreateDTO) {
        ProblemEntity problemEntity = new ProblemEntity(problemCreateDTO.getProblemId(),
                problemCreateDTO.getProblemString(),
                problemCreateDTO.getProblemAudioLocation());

        problemRepository.save(problemEntity);
    }
}
