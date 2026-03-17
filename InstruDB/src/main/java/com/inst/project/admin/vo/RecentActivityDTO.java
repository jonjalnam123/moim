package com.inst.project.admin.vo;

import lombok.Data;

@Data
public class RecentActivityDTO {

    private String activityType;   // 회원 / 공지 / 오류 / 기타
    private String title;
    private String description;
    private String regDate;
	
}
