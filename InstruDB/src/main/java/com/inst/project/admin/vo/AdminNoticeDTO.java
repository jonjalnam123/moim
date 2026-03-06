package com.inst.project.admin.vo;

import lombok.Data;

@Data
public class AdminNoticeDTO {
	
	// 공지사항 ID 
	private String notice_id;
	
	// 공지사항 제목
	private String notice_title;
	
	// 공지사항 내용
	private String notice_cn;
	
	// 공지사항 상단 고정 여부
	private String notice_fix_yn;
	
	// 공지사항 팝업 설정 여부
	private String notice_pop_yn;
	
	// 공지사항 기간 설정 여부
	private String notice_limit_yn;
	
	// 공지사항 시작일시
	private String notcie_str_dt;
	
	// 공지사항 종료일시
	private String notcie_end_dt;
	
	// 공지사항 기간 종료 여부
	private String notice_finish_yn;
	
	// 공지사항 삭제 여부
	private String notice_del_yn;
	
	// 작성자
	private String reg_id;
	
	// 작성날짜
	private String reg_dt;
	
	// 수정자
	private String upd_id;
	
	// 수정날짜
	private String upd_dt;


}
