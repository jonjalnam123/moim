package com.inst.project.admin.service.impl;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminCommMapper {
	
	// 관리자 메뉴명 유니크 값 중복 체크
	int selectMenuNmDupliChk(Map<String, Object> bodyMap);
	
	// 관리자 공통코드 유니크 값 중복 체크
	int selectCommCdDupliChk(Map<String, Object> bodyMap);
	
	// 관리자 부서코드 유니크 값 중복 체크
	int selectUnitCdDupliChk(Map<String, Object> bodyMap);
	
	// 관리자 ID 중복 체크 
	int selectAdminIdDupliChk(Map<String, Object> bodyMap);

	
	

}
