package com.inst.project.admin.vo;

import lombok.Data;

@Data
public class AdminNoticeDTO {
	
	// 공지사항 ID 
	private String noticeId;
	
	// 공지사항 제목
	private String noticeTitle;
	
	// 공지사항 내용
	private String noticeCn;
	
	// 공지사항 상단 고정 여부
	private String noticeFixYn;
	
	// 공지사항 팝업 설정 여부
	private String noticePopYn;
	
	// 공지사항 기간 설정 여부
	private String noticeLimitYn;
	
	// 공지사항 시작일시
	private String noticeStrDt;
	
	// 공지사항 종료일시
	private String noticeEndDt;
	
	// 공지사항 기간 종료 여부
	private String noticeFinishYn;
	
	// 공지사항 삭제 여부
	private String noticeDelYn; 
	
	// 작성자
	private String regId;
	
	// 작성날짜
	private String regDt;
	
	// 수정자
	private String updId;
	
	// 수정날짜
	private String updDt;


}
