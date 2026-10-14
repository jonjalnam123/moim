package com.inst.project.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminUnitDTO;
import com.inst.project.util.PagerUtil;

@Mapper
public interface AdminUserHomeMapper {

	// 메뉴 레벨 1 조회
	List<AdminMenuDTO> selectMenuList();

	// 메뉴 레벨 2 조회
	List<AdminMenuDTO> selectMenuList2();
	
	// 관리자 등급 조회
	List<AdminCommDTO> selectAdminGradeList();
	
	// 관리자 메뉴 상세조회
	AdminMenuDTO adminMenuSelect(AdminMenuDTO adminMenuDTO);
	
	// 관리자 메뉴 상세조회 결과로 부서코드 조회
	List<Map<String, Object>> adminMenuDeptCdSelect(String deptCd);
	
	//관리자 메뉴 등록
	int adminMenuReg(AdminMenuDTO adminMenuDTO);
	
	// 관리자 메뉴 수정
	int adminMenuUpd(AdminMenuDTO adminMenuDTO);
	
	//관리자 메뉴 삭제
	int adminMenuDel(AdminMenuDTO adminMenuDTO);

	// 유닛 조회
	List<AdminUnitDTO> selectUnitAllList();

}
