package com.nor3stbackend.www.batch;

import com.nor3stbackend.www.member.command.domain.aggregate.MemberEntity;
import com.nor3stbackend.www.problem.command.domain.aggregate.ProblemEntity;
import com.nor3stbackend.www.problem.query.application.service.ProblemQueryService;
import com.nor3stbackend.www.solved.command.domain.aggregate.SolvedEntity;
import com.nor3stbackend.www.solved.command.domain.enumType.ProblemType;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class SolvedProcessor implements ItemProcessor<MemberEntity, List<SolvedEntity>> {

    @Autowired
    private ProblemQueryService problemQueryService;

    @Override
    public List<SolvedEntity> process(MemberEntity memberEntity) throws Exception {
        List<ProblemEntity> problemEntityList = problemQueryService.getDailyProblem();

        List<SolvedEntity> dailyTaskList = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            dailyTaskList.add(createSolvedEntity(memberEntity, problemEntityList.get(i), ProblemType.SPEAKING));
        }

        for(int i = 10; i < 20; i++) {
            dailyTaskList.add(createSolvedEntity(memberEntity, problemEntityList.get(i), ProblemType.LISTENING));
        }

        return dailyTaskList;
    }

    private SolvedEntity createSolvedEntity(MemberEntity memberEntity, ProblemEntity problemEntity, ProblemType problemType) {
        return new SolvedEntity(memberEntity, problemEntity, problemType);
    }
}
