package com.inst.project.admin.vo;

import lombok.Data;

@Data
public class AdminMoimDTO {
	
	// 모임장소ID
	private String locateId;
	
	// 모임장소명
	private String locateNm;
	
	// 모임장소 우편번호
	private String locatePostCd;
	
	// 모임장소 주소
	private String locateAddress;
	
	// 모임장소 상세주소
	private String locateDAddress;
	
	// 모임장소 설명
	private String locateCn;
	
	// 모임장소 사용여부
	private String locateUseYn;
	
	// 모임장소 삭제여부
	private String locateDelYn;
	
	// 등록자
	private String regId;
	
	// 등록날짜
	private String regDt;
	
	// 수정자
	private String updId;
	
	// 수정날짜
	private String updDt;

	
}
