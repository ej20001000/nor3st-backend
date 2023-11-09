package com.nor3stbackend.www.solved.command.domain.aggregate;

import com.nor3stbackend.www.solved.command.domain.enumType.SolvedEnum;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "solved_history")
@Getter
public class SolvedHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long solvedHistoryId;

    @ManyToOne
    @JoinColumn(name = "solved_id")
    private SolvedEntity solvedEntity;

    private LocalDateTime solvedDate;

    private String audioUrl;

    @Enumerated(EnumType.STRING)
    private SolvedEnum isSolved;

    public SolvedHistoryEntity() {
    }

    public SolvedHistoryEntity(SolvedEntity solvedEntity, String audioUrl, SolvedEnum isSolved) {
        this.solvedEntity = solvedEntity;
        this.audioUrl = audioUrl;
        this.solvedDate = LocalDateTime.now();
        this.isSolved = isSolved;
    }

    public SolvedHistoryEntity(SolvedEntity solvedEntity, SolvedEnum isSolved) {
        this.solvedEntity = solvedEntity;
        this.solvedDate = LocalDateTime.now();
        this.isSolved = isSolved;
    }
}
