package com.inst.project.admin.service;

import com.inst.project.admin.vo.AdminAopDTO;

public interface AdminAopService {
	
	// 관리자 로그 이력 저장
	void adminLognInsert(AdminAopDTO aopDTO);


}
