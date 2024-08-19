package com.nor3stbackend.www.member.query.application.service;

import com.nor3stbackend.www.config.SecurityUtil;
import com.nor3stbackend.www.member.command.domain.aggregate.MemberEntity;
import com.nor3stbackend.www.member.query.application.dto.MemberQueryDto;
import com.nor3stbackend.www.member.query.domain.vo.AdminDashboardVO;
import com.nor3stbackend.www.member.query.domain.vo.MemberListVO;
import com.nor3stbackend.www.member.query.infra.mapper.MemberMapper;
import com.nor3stbackend.www.solved.query.application.service.SolvedQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberQueryService {
    private final MemberMapper memberMapper;
    private final SolvedQueryService solvedQueryService;

    public MemberQueryDto findByMemberId(Long memberId) {

        MemberEntity memberEntity = memberMapper.findByMemberId(memberId);

        return new MemberQueryDto(memberEntity.getUsername(), memberEntity.getDepartment());
    }

    public List<MemberListVO> getEmployeeList(int page) {
        return memberMapper.getMemberList((page - 1) * 10, memberMapper.getCompanyId(SecurityUtil.getCurrentMemberId()));


    }

    // 테스트 필요
    public AdminDashboardVO getAdminDashboardInfo() {
        Long companyId = memberMapper.getCompanyId(SecurityUtil.getCurrentMemberId());
        String companyPlan = memberMapper.getCompanyPlan(companyId);
        int companyMemberCount = memberMapper.countByCompanyId(companyId);
        double companyDailySolvedRate = solvedQueryService.getCompanyDailySolvedRate(companyId);
        double companyDailySolvedAvgScore = solvedQueryService.getCompanyDailySolvedAvgScore(companyId);
        int companyDailySolvedEmployeeCount = solvedQueryService.getCompanyDailySolvedEmployeeCount(companyId);

        return new AdminDashboardVO(companyPlan, companyMemberCount, 50, companyDailySolvedRate, companyDailySolvedAvgScore, companyDailySolvedEmployeeCount);
    }

    public long getCompanyId(Long memberId) {
        return memberMapper.getCompanyId(memberId);
    }
}
