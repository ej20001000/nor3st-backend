package com.nor3stbackend.www.company.command.domain.aggregate;

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

    public CompanyEntity() {
    }

    public CompanyEntity (String companyName) {
        this.companyName = companyName;
    }

}
