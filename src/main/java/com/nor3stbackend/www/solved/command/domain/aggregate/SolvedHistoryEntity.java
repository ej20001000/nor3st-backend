package com.nor3stbackend.www.solved.command.domain.aggregate;

import com.nor3stbackend.www.solved.command.domain.enumType.SolvedEnum;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

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

    private LocalDate solvedDate;

    private String audioUrl;

    private SolvedEnum isSolved;

    public SolvedHistoryEntity() {
    }

    public SolvedHistoryEntity(SolvedEntity solvedEntity, String audioUrl) {
        this.solvedEntity = solvedEntity;
        this.audioUrl = audioUrl;
        this.solvedDate = LocalDate.now();
    }
}
