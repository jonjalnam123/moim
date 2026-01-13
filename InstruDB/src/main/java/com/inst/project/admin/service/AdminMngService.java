package com.inst.project.admin.service;

import java.util.List;

import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminMenuDTO;

public interface AdminMngService {
	
	// 공통코드 레벨 1 조회
	List<AdminCommDTO> selectCommList();
	
	// 공통코드 레벨 2 조회
	List<AdminCommDTO> selectCommList2();

	// 메뉴 레벨 1 조회
	List<AdminMenuDTO> selectMenuList();

	// 메뉴 레벨 2 조회
	List<AdminMenuDTO> selectMenuList2();

}
