package com.inst.project.aop.vo;

import lombok.Data;

@Data
public class AopDTO {

	private Long logId;        // PK
	private String classNm;    // 컨트롤러 클래스명
	private String methodNm;   // 메소드명
	private String reqUri;     // 요청 URI
	private String httpMethod; // GET / POST ...
	private String clientIp;   // 클라이언트 IP
	private String userId;     // 세션에 있는 유저 ID (없으면 null)
	private String regDt;      // 등록일시 (문자열로 받을거면)
	private String paramJson;      // 등록일시 (문자열로 받을거면)
	
}
