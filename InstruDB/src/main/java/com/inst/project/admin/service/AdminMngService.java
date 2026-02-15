package com.inst.project.admin.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminUnitDTO;
import com.inst.project.utill.PagerUtil;

public interface AdminMngService {
	
	// 공통코드 레벨 1 조회
	List<AdminCommDTO> selectCommList();
	
	// 공통코드 레벨 2 조회
	List<AdminCommDTO> selectCommList2();
	
	// 관리자 공통코드 상세 조회
	AdminCommDTO adminCommSelect(AdminCommDTO adminCommDTO);
	
	// 관리자 공통코드 등록
	int adminCommReg(AdminCommDTO adminCommDTO, HttpServletRequest req);
	
	// 관리자 공통코드 수정
	int adminCommUpd(AdminCommDTO adminCommDTO, HttpServletRequest req);
	
	// 관리자 공통코드 삭제
	int adminCommDel(AdminCommDTO adminCommDTO, HttpServletRequest req);

	// 메뉴 레벨 1 조회
	List<AdminMenuDTO> selectMenuList();

	// 메뉴 레벨 2 조회
	List<AdminMenuDTO> selectMenuList2();
	
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

	// 유닛 레벨 1 조회
	List<AdminUnitDTO> selectUnitList();

	// 유닛 레벨 2 조회
	List<AdminUnitDTO> selectUnitList2();

	// 유닛 레벨 3 조회
	List<AdminUnitDTO> selectUnitList3();
	
	// 관리자 부서 상세 조회
	AdminUnitDTO adminUnitSelect(AdminUnitDTO adminUnitDTO);
	
	// 관리자 부서 등록
	int adminUnitReg(AdminUnitDTO adminUnitDTO, HttpServletRequest req);
	
	// 관리자 부서 수정
	int adminUnitUpd(AdminUnitDTO adminUnitDTO, HttpServletRequest req);

	// 관리자 부서 삭제
	int adminUnitDel(AdminUnitDTO adminUnitDTO, HttpServletRequest req);

	// 관리자 관리 화면 조회
	List<AdminDTO> selectAdminUser(PagerUtil pager);
	
	// 관리자 등급 조회
	List<AdminCommDTO> selectAdminGradeList();
	
	// 관리자 관리자 정보 조회
	AdminDTO selectAdminUserInfo(AdminDTO adminDTO);
	
	// 관리자 유닛 팀 조회
	List<Map<String, Object>> selectAdminTeamList(String adminUnitId);

	// 관리자 유닛 직책 조회
	List<Map<String, Object>> selectAdminPositionList(String adminUnitId);
	
	// 관리자 등록
	int adminUserReg(AdminDTO adminDTO, HttpServletRequest req);
	
	// 관리자 수정
	int adminUserUpd(AdminDTO adminDTO, HttpServletRequest req);

	// 관리자 삭제
	int adminUserDel(AdminDTO adminDTO, HttpServletRequest req);
	
	// 관리자 가입승인관리 조회
	List<AdminDTO> selectAdminUserAcceptInfo( PagerUtil pager );
	
	// 관리자 가입승인관리 상세 조회
	AdminDTO selectAdminUserAcceptDtlInfo(AdminDTO adminDTO);
	
	// 관리자 가입승인관리 승인 진행
	int adminUserAcceptUpd(AdminDTO adminDTO, HttpServletRequest req);
	
	// 관리자 가입승인관리 반려 진행
	int adminUserAcceptDel(AdminDTO adminDTO, HttpServletRequest req);

}
