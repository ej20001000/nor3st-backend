package com.nor3stbackend.www.solved.command.domain.aggregate;

import com.nor3stbackend.www.member.command.domain.aggregate.MemberEntity;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "solved")
@Entity
@Getter
public class SolvedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long solvedId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    private String audioUrl;

    private LocalDate solvedDate;

    public SolvedEntity() {
    }

    public SolvedEntity(MemberEntity memberEntity, String audioUrl) {
        this.memberEntity = memberEntity;
        this.audioUrl = audioUrl;
        this.solvedDate = LocalDate.now();
    }
}
