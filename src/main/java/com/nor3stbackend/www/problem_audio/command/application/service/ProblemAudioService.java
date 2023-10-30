package com.nor3stbackend.www.problem_audio.command.application.service;

import com.nor3stbackend.www.problem.command.domain.aggregate.ProblemEntity;
import com.nor3stbackend.www.problem_audio.command.domain.aggregate.ProblemAudioEntity;
import com.nor3stbackend.www.problem_audio.command.infra.repository.ProblemAudioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProblemAudioService {

    private final ProblemAudioRepository problemAudioRepository;

    public ProblemAudioService(ProblemAudioRepository problemAudioRepository) {
        this.problemAudioRepository = problemAudioRepository;
    }

    @Transactional
    public void createProblemAudio(String gender, String audioUrl, ProblemEntity problemEntity) {

        ProblemAudioEntity problemAudioEntity = new ProblemAudioEntity(gender, audioUrl, problemEntity);

        problemAudioRepository.save(problemAudioEntity);
    }
}
