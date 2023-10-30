package com.nor3stbackend.www.member.query.infra.mapper;

import com.nor3stbackend.www.member.command.domain.aggregate.MemberEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    MemberEntity findByMemberId(Long memberId);
    Integer countByCompanyId(Long companyId);
}
