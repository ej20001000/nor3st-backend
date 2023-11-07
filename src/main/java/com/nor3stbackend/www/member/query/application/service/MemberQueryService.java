package com.nor3stbackend.www.member.query.application.service;

import com.nor3stbackend.www.config.SecurityUtil;
import com.nor3stbackend.www.member.command.domain.aggregate.MemberEntity;
import com.nor3stbackend.www.member.query.application.dto.MemberQueryDto;
import com.nor3stbackend.www.member.query.domain.vo.MemberListVO;
import com.nor3stbackend.www.member.query.infra.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberQueryService {
    private final MemberMapper memberMapper;

    public MemberQueryService (MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public MemberQueryDto findByMemberId(Long memberId) {
        System.out.println("memberId: " + memberId);

        MemberEntity memberEntity = memberMapper.findByMemberId(memberId);

        MemberQueryDto memberQueryDto = new MemberQueryDto(memberEntity.getUsername(), memberEntity.getDepartment());

        return memberQueryDto;
    }

    public List<MemberListVO> getEmployeeList(int page) {
        List<MemberListVO> memberListVO = memberMapper.getMemberList((page - 1) * 10, memberMapper.getCompanyId(SecurityUtil.getCurrentMemberId()));

        return memberListVO;
    }
}
