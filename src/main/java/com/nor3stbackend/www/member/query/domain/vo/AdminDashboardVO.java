package com.nor3stbackend.www.member.query.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminDashboardVO {
    private String companyPlan;
    private Integer companyMemberCount;
    private Integer companyMemberCountLimit;
    private double employeeSolveRate;
    private double employeeAvgScore;
    private int employeeDailySolveCount;
}
