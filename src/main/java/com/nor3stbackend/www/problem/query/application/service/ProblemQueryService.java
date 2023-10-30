package com.nor3stbackend.www.problem.query.application.service;

import com.nor3stbackend.www.problem.command.domain.aggregate.ProblemEntity;
import com.nor3stbackend.www.problem.query.infra.mapper.ProblemMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemQueryService {

    private final ProblemMapper problemMapper;

    public ProblemQueryService(ProblemMapper problemMapper) {
        this.problemMapper = problemMapper;
    }

    public List<ProblemEntity> getDailyProblem() {
        return problemMapper.getDailyProblem();
    }
}
