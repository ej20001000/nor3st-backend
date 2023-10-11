package com.nor3stbackend.www.problem.command.infra.repository;

import com.nor3stbackend.www.problem.command.domain.aggregate.ProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<ProblemEntity, Long> {

}
