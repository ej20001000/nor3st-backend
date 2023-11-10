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
        System.out.println("memberId: " + memberId);

        MemberEntity memberEntity = memberMapper.findByMemberId(memberId);

        MemberQueryDto memberQueryDto = new MemberQueryDto(memberEntity.getUsername(), memberEntity.getDepartment());

        return memberQueryDto;
    }

    public List<MemberListVO> getEmployeeList(int page) {
        List<MemberListVO> memberListVO = memberMapper.getMemberList((page - 1) * 10, memberMapper.getCompanyId(SecurityUtil.getCurrentMemberId()));

        return memberListVO;
    }

    // 테스트 필요
    public AdminDashboardVO getAdminDashboardInfo() {
        Long companyId = memberMapper.getCompanyId(SecurityUtil.getCurrentMemberId());
        String companyPlan = memberMapper.getCompanyPlan(companyId);
        int companyMemberCount = memberMapper.getCompanyMemberCount(companyId);
        double companyDailySolvedRate = solvedQueryService.getCompanyDailySolvedRate(companyId);
        double companyDailySolvedAvgScore = solvedQueryService.getCompanyDailySolvedAvgScore(companyId);
        int companyDailySolvedEmployeeCount = solvedQueryService.getCompanyDailySolvedEmployeeCount(companyId);

        AdminDashboardVO adminDashboardVO = new AdminDashboardVO(companyPlan, companyMemberCount, 50, companyDailySolvedRate, companyDailySolvedAvgScore, companyDailySolvedEmployeeCount);

        return adminDashboardVO;
    }
}
