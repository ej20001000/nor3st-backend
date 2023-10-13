package com.nor3stbackend.www.member.command.infra.repository;

import com.nor3stbackend.www.member.command.domain.aggregate.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByMemberId(String memberId);
}