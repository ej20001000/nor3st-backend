package com.nor3stbackend.www.problem_audio.command.infra.repository;

import com.nor3stbackend.www.problem_audio.command.domain.aggregate.ProblemAudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemAudioRepository extends JpaRepository<ProblemAudioEntity, Long> {
}
