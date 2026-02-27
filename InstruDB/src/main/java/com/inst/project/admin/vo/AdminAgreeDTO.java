package com.inst.project.admin.vo;

import lombok.Data;

@Data
public class AdminAgreeDTO {
	
	// 약관동의ID
	private String agreeId;
	
	// 사용자ID
	private String agreeUserId;
	
	//  1번 약관동의여부 ( Y : 확인, N : 미확인 )
	private String agreeService;
	
	// 2번 약관동의여부 ( Y : 확인, N : 미확인 )
	private String agreePrivacy;
	
	// 3번 약관동의여부 ( Y : 확인, N : 미확인 )
	private String agreeMarketing;
	
	// 4번 약관동의여부 ( Y : 확인, N : 미확인 )
	private String agreeConsign;
	
	// 약관동의구분 ( 00 : 관리자, 01 : 일반 사용자 )
	private String agreeGb;
	
	// 등록자
	private String regId;
	
	// 등록날짜
	private String regDt;
	
	// 수정자
	private String updId;
	
	// 수정날짜
	private String updDt;

}
