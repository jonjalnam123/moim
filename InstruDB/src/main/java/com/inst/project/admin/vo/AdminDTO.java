package com.inst.project.admin.vo;

import lombok.Data;

@Data
public class AdminDTO {
	
	// 관리자번호
	private String adminNo;
	
	// 관리자 아이디
	private String adminId;
	
	// 관리자 비밀번호
	private String adminPw;
	
	// 관리자 이름
	private String adminNm;
	
	// 관리자 핸드폰
	private String adminPh;
	
	// 관리자 우편번호
	private String adminPostCd;
	
	// 관리자 주소
	private String adminAddress;
	
	// 관리자 핸상세주소
	private String adminDAddress;
	
	// 관리자 부서코드
	private String adminDeptCd; 
	
	// 관리자 부서이름
	private String adminDeptNm; 
	
	// 관리자 팀코드
	private String adminTeamCd;
	
	// 관리자 팀이름
	private String adminTeamNm;
	
	// 관리자 직책코드
	private String adminPositionCd;
	
	// 관리자 직책이름
	private String adminPositionNm;
	
	// 관리자 아이피
	private String adminIp;
	
	// 관리자 삭제여부
	private String adminDelYn;
	
	// 관리자 삭제여부 이름
	private String adminDelYnNm;
	
	// 관리자 설명
	private String adminCn;
	
	// 관리자 성별
	private String adminGender;
	
	// 관리자 성별 이름
	private String adminGenderNm;
	
	// 관리자 등록구분 ( 00 : 본인가입, 01 : 슈퍼관리자 등록 )
	private String adminRegGb;
	
	// 관리자 등급코드 ( 00 : 관리자, 01 : 운영자, 02 : 매니저 )
	private String adminGradeCd;
	
	// 관리자 등록승인여부 ( Y : 승인, N : 미승인, R : 반려)
	private String adminRegAccept;
	
	// 관리자 등록승인여부 이름
	private String adminRegAcceptNm;
	
	// 관리자 가입승인 반려사유
	private String adminRejectCn;
	
	// 관리자 등록자
	private String regId;
	
	// 관리자 등록날짜
	private String regDt;
	
	// 관리자 수정자
	private String updId;
	
	// 관리자 수정날짜
	private String updDt;
	
}
