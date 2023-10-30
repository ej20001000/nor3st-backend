package com.nor3stbackend.www.problem_audio.command.domain.aggregate;

import com.nor3stbackend.www.problem.command.domain.aggregate.ProblemEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "problem_audio")
public class ProblemAudioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemAudioId;

    private String gender;

    private String audioUrl;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private ProblemEntity problemEntity;

    public ProblemAudioEntity(String gender, String audioUrl, ProblemEntity problemEntity) {
        this.gender = gender;
        this.audioUrl = audioUrl;
        this.problemEntity = problemEntity;
    }
}
