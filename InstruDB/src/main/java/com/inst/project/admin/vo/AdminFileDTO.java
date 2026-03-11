package com.inst.project.admin.vo;

import lombok.Data;

@Data
public class AdminFileDTO {
	
	// 파일ID
	private String fileId;
	
	// 파일원본명
	private String fileOrgNm;
	
	// 파일명
	private String fileNm;
	
	// 파일경로
	private String filePath;
	
	// 파일크기
	private long fileSize;
	
	// 파일 확장자
	private String fileExt;
	
	// 파일이등록된페이지
	private String refType;
	
	// 파일이등로된페이지ID
	private String refId;
	
	// 파일썸네일이름
	private String fileThumbNm;
	
	// 파일썸네일경로
	private String fileThumbPath;

	// 등록자
	private String regId;
	
	// 등록날짜
	private String regDt;
	
	// 수정자
	private String updId;
	
	// 수정날짜
	private String updDt;

}
