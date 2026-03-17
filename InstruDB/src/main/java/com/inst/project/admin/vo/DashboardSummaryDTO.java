package com.inst.project.admin.vo;

import lombok.Data;

@Data
public class DashboardSummaryDTO {
	
    private int totalMemberCnt;
    private int memberGrowthRate;
    private int totalMeetingCnt;
    private int monthMeetingCnt;
    private int avgAttendRate;
    private int activeGroupCnt;
    private int memberGoalRate;
    private int meetingGoalRate;
    private int noticeReadRate;
    private int newMemberCnt;
    private int weekMeetingCnt;

}
