package com.inst.project.admin.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminUnitDTO;
import com.inst.project.util.PagerUtil;

public interface AdminUserHomeService {
	
	// 관리자 메뉴 레벨 1 조회
	List<AdminMenuDTO> selectMenuList();

	// 관리자 메뉴 레벨 2 조회
	List<AdminMenuDTO> selectMenuList2();

	// 관리자 메뉴 등급 조회
	List<AdminCommDTO> selectAdminGradeList();
	
	// 관리자 메뉴 상세 조회
	AdminMenuDTO adminMenuSelect(AdminMenuDTO adminMenuDTO);
	
	// 관리자 메뉴 상세조회 결과로 부서코드 조회
	List<Map<String, Object>> adminMenuDeptCdSelect(AdminMenuDTO adminMenuDTO);
	
	//관리자 메뉴 등록
	int adminMenuReg(AdminMenuDTO adminMenuDTO, HttpServletRequest req);
	
	// 관리자 메뉴 수정
	int adminMenuUpd(AdminMenuDTO adminMenuDTO, HttpServletRequest req);
	
	// 관리자 메뉴 삭제
	int adminMenuDel(AdminMenuDTO adminMenuDTO, HttpServletRequest req);
	
	// 유닛 조회
	List<AdminUnitDTO> selectUnitAllList();

}
