package com.nor3stbackend.www.company.command.domain.aggregate;

import com.nor3stbackend.www.company.command.domain.enumType.PlanEnum;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "company")
@Getter
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    private String companyName;

    @Enumerated(EnumType.STRING)
    private PlanEnum plan;

    public CompanyEntity() {
    }

    public CompanyEntity (String companyName, PlanEnum plan) {
        this.companyName = companyName;
        this.plan = plan;
    }

}
