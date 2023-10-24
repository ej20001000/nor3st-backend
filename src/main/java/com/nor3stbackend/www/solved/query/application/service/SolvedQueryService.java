package com.nor3stbackend.www.solved.query.application.service;

import com.nor3stbackend.www.config.SecurityUtil;
import com.nor3stbackend.www.solved.command.domain.aggregate.SolvedEntity;
import com.nor3stbackend.www.solved.query.infra.mapper.SolvedMapper;
import org.springframework.stereotype.Service;

@Service
public class SolvedQueryService {

    private final SolvedMapper solvedMapper;

    public SolvedQueryService (SolvedMapper solvedMapper) {
        this.solvedMapper = solvedMapper;
    }

    public SolvedEntity getMySolvedList() {
        return solvedMapper.getMySolvedList(SecurityUtil.getCurrentMemberId());
    }
}
