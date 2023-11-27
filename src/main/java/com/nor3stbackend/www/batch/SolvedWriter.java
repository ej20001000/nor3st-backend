package com.nor3stbackend.www.batch;

import com.nor3stbackend.www.solved.command.domain.aggregate.SolvedEntity;
import com.nor3stbackend.www.solved.command.infra.repository.SolvedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class SolvedWriter implements ItemWriter<List<SolvedEntity>> {

    @Autowired
    private SolvedRepository solvedRepository;

    @Override
    public void write(List<? extends List<SolvedEntity>> items) throws Exception {
        List<SolvedEntity> dailyTaskList = items.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        solvedRepository.saveAll(dailyTaskList);
    }
}
