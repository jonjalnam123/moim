package com.inst.project.admin.vo;

import lombok.Data;

@Data
public class AdminAopDTO {
	
	// 로그 ID
	private String logId;        
	
	// 클래스명
	private String classNm;    
	
	// 메소드명
	private String methodNm;   
	
	// 호출 경로
	private String reqUri;    
	
	// 통신방법
	private String httpMethod; 
	
	// 사용자IP
	private String clientIp;  
	
	// 등록자
	private String regId;     
	
	// 등록날짜
	private String regDt;     
	
	// 파라미터
	private String paramJson;     
	
}
