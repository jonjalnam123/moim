package com.inst.project.admin.vo;

import lombok.Data;

@Data
public class MeetingScheduleDTO {

    private String startTime;
    private String meetingTitle;
    private String location;
    private int expectedCnt;
}
