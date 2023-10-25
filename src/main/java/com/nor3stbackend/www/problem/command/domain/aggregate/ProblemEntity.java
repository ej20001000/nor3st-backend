package com.nor3stbackend.www.problem.command.domain.aggregate;

import lombok.Getter;

import javax.persistence.*;


@Entity
@Getter
@Table(name = "problem")
public class ProblemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemId;

    private String koreanContent;

    private String vietContent;



    public ProblemEntity() {
    }
    public ProblemEntity(String koreanContent, String vietContents) {
        this.koreanContent = koreanContent;
        this.vietContent = vietContent;
    }
}
