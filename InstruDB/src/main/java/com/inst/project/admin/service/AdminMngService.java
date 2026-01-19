package com.inst.project.admin.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminUnitDTO;

public interface AdminMngService {
	
	// 공통코드 레벨 1 조회
	List<AdminCommDTO> selectCommList();
	
	// 공통코드 레벨 2 조회
	List<AdminCommDTO> selectCommList2();

	// 메뉴 레벨 1 조회
	List<AdminMenuDTO> selectMenuList();

	// 메뉴 레벨 2 조회
	List<AdminMenuDTO> selectMenuList2();
	
	//관리자 메뉴 등록
	int adminMenuReg(AdminMenuDTO adminMenuDTO, HttpServletRequest req);
	
	// 관리자 메뉴 삭제
	int adminMenuDel(AdminMenuDTO adminMenuDTO, HttpServletRequest req);
	
	// 유닛 조회
	List<AdminUnitDTO> selectUnitAllList();

	// 유닛 레벨 1 조회
	List<AdminUnitDTO> selectUnitList();

	// 유닛 레벨 2 조회
	List<AdminUnitDTO> selectUnitList2();

	// 유닛 레벨 3 조회
	List<AdminUnitDTO> selectUnitList3();




}
