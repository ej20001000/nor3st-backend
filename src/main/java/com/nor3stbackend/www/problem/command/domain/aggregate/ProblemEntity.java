package com.nor3stbackend.www.problem.command.domain.aggregate;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProblemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long problemId;

    @Column
    private String problemString;

    @Column
    private String problemAudioLocation;

    public ProblemEntity() {
    }

    public ProblemEntity(Long problemId, String problemString, String problemAudioLocation) {
        this.problemId = problemId;
        this.problemString = problemString;
        this.problemAudioLocation = problemAudioLocation;
    }
}
