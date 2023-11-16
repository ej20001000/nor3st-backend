package com.nor3stbackend.www.company.command.infra.repository;

import com.nor3stbackend.www.company.command.domain.aggregate.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    Optional<CompanyEntity> findByCompanyName(String companyName);
    CompanyEntity findByMemberId(Long memberId);
}
