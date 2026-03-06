package com.inst.project.admin.service;

import java.util.Map;

public interface AdminCommService {

	// 관리자 유니크 값 중복 체크
	String selectUniqueDupliChk(Map<String, Object> bodyMap);

	// 관리자 유니크 값 생성
	String selectUniqueId();

}
