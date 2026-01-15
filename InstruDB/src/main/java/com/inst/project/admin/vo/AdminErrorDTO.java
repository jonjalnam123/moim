package com.inst.project.admin.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AdminErrorDTO {
	
	private String adminErrorCd;
	private String adminErrorMsg;
	private LocalDateTime adminErrorDate;
	private String regId;
	private String regDt;
	private String updId;
	private String updDt;
	
}
