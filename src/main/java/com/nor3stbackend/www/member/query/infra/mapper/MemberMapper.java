package com.nor3stbackend.www.member.query.infra.mapper;

import com.nor3stbackend.www.member.command.domain.aggregate.MemberEntity;
import com.nor3stbackend.www.member.query.domain.vo.MemberListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    MemberEntity findByMemberId(Long memberId);
    Integer countByCompanyId(Long companyId);

    List<MemberListVO> getMemberList(int offset, Long companyId);

    Long getCompanyId(Long currentMemberId);

    String getCompanyPlan(Long companyId);

}
