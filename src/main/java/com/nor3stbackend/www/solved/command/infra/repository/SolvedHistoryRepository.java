package com.nor3stbackend.www.solved.command.infra.repository;

import com.nor3stbackend.www.solved.command.domain.aggregate.SolvedHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolvedHistoryRepository extends JpaRepository<SolvedHistoryEntity, Long> {

}
