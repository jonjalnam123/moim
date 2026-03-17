package com.inst.project.admin.vo;

import lombok.Data;

@Data
public class NoticeDTO {

    private String noticeId;
    private String noticeType;   // 일반 / 중요 / 이벤트
    private String title;
    private String summary;
    private String regDate;
}
