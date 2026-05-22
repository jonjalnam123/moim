package com.inst.project.admin.vo;

import lombok.Data;

@Data
public class AdminMoimDTO {
	
	// 모임ID
	private String moimId;
	
	// 모임명
	private String moimTitle;
	
	// 모임날짜
	private String moimDt;
	
	// 모임최대인원
	private String moimMaxCnt;
	
	// 모임삭제여부
	private String moimDelYn;
	
	// 모임장소ID
	private String moimLocateId;
	
	// 모임장소명
	private String moimLocateNm;
	
	// 모임내용
	private String moimCn;
	
	// 모임구분 ( 01 : 정모, 02 : 번개, 03 : 그 외 )
	private String moimGb;
	
	// 모임구분명
	private String moimGbNm;
	
	// 등록자
	private String regId;
	
	// 등록날짜
	private String regDt;
	
	// 수정자
	private String updId;
	
	// 수정날짜
	private String updDt;

	
}
