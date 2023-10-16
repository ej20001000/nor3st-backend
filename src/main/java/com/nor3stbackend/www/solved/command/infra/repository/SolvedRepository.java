package com.nor3stbackend.www.solved.command.infra.repository;

import com.nor3stbackend.www.solved.command.domain.aggregate.SolvedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolvedRepository extends JpaRepository<SolvedEntity, Long> {
}
