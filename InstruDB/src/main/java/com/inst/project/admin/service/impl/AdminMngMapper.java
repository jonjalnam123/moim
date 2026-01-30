package com.inst.project.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminUnitDTO;

@Mapper
public interface AdminMngMapper {

	// 공통코드 레벨 1 조회
	List<AdminCommDTO> selectCommList();
	
	// 공통코드 레벨 2 조회
	List<AdminCommDTO> selectCommList2();
	
	// 관리자 공통코드 상세조회
	AdminCommDTO adminCommSelect(AdminCommDTO adminCommDTO);
	
	// 관리자 공통코드 등록
	int adminCommReg(AdminCommDTO adminCommDTO);
	
	// 관리자 공통코드 수정
	int adminCommUpd(AdminCommDTO adminCommDTO);
	
	// 관리자 공통코드 삭제
	int adminCommDel(AdminCommDTO adminCommDTO);

	// 메뉴 레벨 1 조회
	List<AdminMenuDTO> selectMenuList();

	// 메뉴 레벨 2 조회
	List<AdminMenuDTO> selectMenuList2();
	
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
	
	// 유닛 레벨 1 조회
	List<AdminUnitDTO> selectUnitList();

	// 유닛 레벨 2 조회
	List<AdminUnitDTO> selectUnitList2();

	// 유닛 레벨 3 조회
	List<AdminUnitDTO> selectUnitList3();
	
	// 관리자 부서 상세 조회
	AdminUnitDTO adminUnitSelect(AdminUnitDTO adminUnitDTO);
	
	// 관리자 부서 등록
	int adminUnitReg(AdminUnitDTO adminUnitDTO);
	
	// 관리자 부서 수정
	int adminUnitUpd(AdminUnitDTO adminUnitDTO);

	// 관리자 부서 삭제
	int adminUnitDel(AdminUnitDTO adminUnitDTO);

}
