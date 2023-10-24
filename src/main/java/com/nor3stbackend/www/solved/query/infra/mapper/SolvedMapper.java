package com.nor3stbackend.www.solved.query.infra.mapper;

import com.nor3stbackend.www.solved.command.domain.aggregate.SolvedEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SolvedMapper {
    SolvedEntity getMySolvedList(Long memberId);
}
