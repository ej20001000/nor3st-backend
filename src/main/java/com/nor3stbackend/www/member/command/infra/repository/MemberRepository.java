package com.nor3stbackend.www.member.command.infra.repository;

import com.nor3stbackend.www.member.command.domain.aggregate.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String memberId);
}