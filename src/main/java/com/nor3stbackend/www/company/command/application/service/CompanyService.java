package com.nor3stbackend.www.company.command.application.service;

import com.nor3stbackend.www.company.command.domain.aggregate.CompanyEntity;
import com.nor3stbackend.www.company.command.domain.enumType.PlanEnum;
import com.nor3stbackend.www.company.command.infra.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyEntity insertCompany(String companyName) {
        return companyRepository.save(new CompanyEntity(companyName, PlanEnum.Basic));
    }

    public Optional<CompanyEntity> getCompany(Long companyId) {
        return companyRepository.findById(companyId);
    }
}
